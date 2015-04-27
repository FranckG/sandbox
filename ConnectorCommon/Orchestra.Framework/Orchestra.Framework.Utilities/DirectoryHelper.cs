// ----------------------------------------------------------------------------------------------------
// File Name: DirectoryHelper.cs
// Project: Orchestra.Framework.Utilities
// Copyright (c) Thales, 2013 - 2013. All rights reserved.
// ----------------------------------------------------------------------------------------------------

namespace Orchestra.Framework.Utilities
{
    using System;
    using System.Runtime.InteropServices;
    using FILETIME = System.Runtime.InteropServices.ComTypes.FILETIME;

    /// <summary>
    /// Class DirectoryHelper
    /// </summary>
    public static class DirectoryHelper
    {
        // ReSharper disable UnusedMember.Local

        #region ECreationDisposition enum
        private enum ECreationDisposition : uint
        {
            New = 1,
            CreateAlways = 2,
            OpenExisting = 3,
            OpenAlways = 4,
            TruncateExisting = 5
        }
        #endregion

        #region EFileAccess enum
        [Flags]
        private enum EFileAccess : uint
        {
            GenericRead = 0x80000000,
            GenericWrite = 0x40000000,
            GenericExecute = 0x20000000,
            GenericAll = 0x10000000
        }
        #endregion

        #region EFileAttributes enum
        [Flags]
        private enum EFileAttributes : uint
        {
            Readonly = 0x00000001,
            Hidden = 0x00000002,
            System = 0x00000004,
            Directory = 0x00000010,
            Archive = 0x00000020,
            Device = 0x00000040,
            Normal = 0x00000080,
            Temporary = 0x00000100,
            SparseFile = 0x00000200,
            ReparsePoint = 0x00000400,
            Compressed = 0x00000800,
            Offline = 0x00001000,
            NotContentIndexed = 0x00002000,
            Encrypted = 0x00004000,
            Write_Through = 0x80000000,
            Overlapped = 0x40000000,
            NoBuffering = 0x20000000,
            RandomAccess = 0x10000000,
            SequentialScan = 0x08000000,
            DeleteOnClose = 0x04000000,
            BackupSemantics = 0x02000000,
            PosixSemantics = 0x01000000,
            OpenReparsePoint = 0x00200000,
            OpenNoRecall = 0x00100000,
            FirstPipeInstance = 0x00080000
        }
        #endregion

        #region EFileShare enum
        [Flags]
        private enum EFileShare : uint
        {
            None = 0x00000000,
            Read = 0x00000001,
            Write = 0x00000002,
            Delete = 0x00000004
        }
        #endregion

        // ReSharper restore UnusedMember.Local

        private const short InvalidHandleValue = -1;

        /// <summary>
        /// Determines if the file names point to same file/folder.
        /// </summary>
        /// <param name="fileName1">The file name1.</param>
        /// <param name="fileName2">The file name2.</param>
        /// <returns><c>true</c> if <paramref name="fileName1"/> and <paramref name="fileName2"/> point to same file, <c>false</c> otherwise</returns>
        public static bool FileNamesPointToSameFile(string fileName1, string fileName2)
        {
            bool result = false;

            IntPtr fileHandle1 = CreateFile(
                fileName1,
                0,
                (uint)(EFileShare.Read | EFileShare.Delete | EFileShare.Write),
                IntPtr.Zero,
                (uint)ECreationDisposition.OpenExisting,
                (uint)(EFileAttributes.Normal | EFileAttributes.BackupSemantics),
                IntPtr.Zero);
            if (fileHandle1.ToInt32() != InvalidHandleValue)
            {
                ByHandleFileInformation info1;
                bool rc = GetFileInformationByHandle(fileHandle1, out info1);
                if (rc)
                {
                    IntPtr fileHandle2 = CreateFile(
                        fileName2,
                        0,
                        (uint)(EFileShare.Read | EFileShare.Delete | EFileShare.Write),
                        IntPtr.Zero,
                        (uint)ECreationDisposition.OpenExisting,
                        (uint)(EFileAttributes.Normal | EFileAttributes.BackupSemantics),
                        IntPtr.Zero);
                    if (fileHandle2.ToInt32() != InvalidHandleValue)
                    {
                        ByHandleFileInformation info2;
                        rc = GetFileInformationByHandle(fileHandle2, out info2);
                        if (rc)
                        {
                            if ((info1.FileIndexHigh == info2.FileIndexHigh)
                                && (info1.FileIndexLow == info2.FileIndexLow)
                                && (info1.VolumeSerialNumber == info2.VolumeSerialNumber))
                            {
                                result = true;
                            }
                        }
                    }
                    CloseHandle(fileHandle2);
                }
            }
            CloseHandle(fileHandle1);
            return result;
        }

        [DllImport("kernel32.dll", SetLastError = true)]
        [return: MarshalAs(UnmanagedType.Bool)]
        private static extern bool CloseHandle(IntPtr hObject);

        [DllImport("kernel32.dll", CharSet = CharSet.Auto, CallingConvention = CallingConvention.StdCall,
            SetLastError = true)]
        private static extern IntPtr CreateFile(
            String lpFileName,
            UInt32 dwDesiredAccess,
            UInt32 dwShareMode,
            IntPtr lpSecurityAttributes,
            UInt32 dwCreationDisposition,
            UInt32 dwFlagsAndAttributes,
            IntPtr hTemplateFile);

        [DllImport("kernel32.dll", SetLastError = true)]
        private static extern bool GetFileInformationByHandle(
            IntPtr hFile, out ByHandleFileInformation lpFileInformation);

        #region Nested type: BY_HANDLE_FILE_INFORMATION
#pragma warning disable 169
#pragma warning disable 649
        private struct ByHandleFileInformation
        {
            public FILETIME CreationTime;
            public uint FileAttributes;
            public uint FileIndexHigh;
            public uint FileIndexLow;
            public uint FileSizeHigh;
            public uint FileSizeLow;
            public FILETIME LastAccessTime;
            public FILETIME LastWriteTime;
            public uint NumberOfLinks;
            public uint VolumeSerialNumber;
        }
#pragma warning restore 649
#pragma warning restore 169
        #endregion
    }
}