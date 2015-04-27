// CalculCRC.cpp : implementation file
//

#include "stdafx.h"
#include "CRC.h"
#include "CalculCRC.h"

#ifdef _DEBUG
#define new DEBUG_NEW
#undef THIS_FILE
static char THIS_FILE[] = __FILE__;
#endif

/////////////////////////////////////////////////////////////////////////////
// CCalculCRC

IMPLEMENT_DYNCREATE(CCalculCRC, CCmdTarget)

CCalculCRC::CCalculCRC()
{
	EnableAutomation();
	InitialiserTableCRC32();
	// To keep the application running as long as an OLE automation 
	//	object is active, the constructor calls AfxOleLockApp.
	
	AfxOleLockApp();
}

CCalculCRC::~CCalculCRC()
{
	// To terminate the application when all objects created with
	// 	with OLE automation, the destructor calls AfxOleUnlockApp.
	
	AfxOleUnlockApp();
}


void CCalculCRC::OnFinalRelease()
{
	// When the last reference for an automation object is released
	// OnFinalRelease is called.  The base class will automatically
	// deletes the object.  Add additional cleanup required for your
	// object before calling the base class.

	CCmdTarget::OnFinalRelease();
}


BEGIN_MESSAGE_MAP(CCalculCRC, CCmdTarget)
	//{{AFX_MSG_MAP(CCalculCRC)
		// NOTE - the ClassWizard will add and remove mapping macros here.
	//}}AFX_MSG_MAP
END_MESSAGE_MAP()

BEGIN_DISPATCH_MAP(CCalculCRC, CCmdTarget)
	//{{AFX_DISPATCH_MAP(CCalculCRC)
	DISP_FUNCTION(CCalculCRC, "GetCRC", GetCRC, VT_BSTR, VTS_BSTR)
	DISP_FUNCTION(CCalculCRC, "GetCRCForAString", GetCRCForAString, VT_BSTR, VTS_BSTR)
	//}}AFX_DISPATCH_MAP
END_DISPATCH_MAP()

// Note: we add support for IID_ICalculCRC to support typesafe binding
//  from VBA.  This IID must match the GUID that is attached to the 
//  dispinterface in the .ODL file.

// {6B040DBB-0851-412D-826A-97B3F96DA404}
static const IID IID_ICalculCRC =
{ 0x6b040dbb, 0x851, 0x412d, { 0x82, 0x6a, 0x97, 0xb3, 0xf9, 0x6d, 0xa4, 0x4 } };

BEGIN_INTERFACE_MAP(CCalculCRC, CCmdTarget)
	INTERFACE_PART(CCalculCRC, IID_ICalculCRC, Dispatch)
END_INTERFACE_MAP()

// {1C8388F9-8A08-4F89-9B0F-0749119E2EC2}
IMPLEMENT_OLECREATE(CCalculCRC, "CRC.CalculCRC", 0x1c8388f9, 0x8a08, 0x4f89, 0x9b, 0xf, 0x7, 0x49, 0x11, 0x9e, 0x2e, 0xc2)

/////////////////////////////////////////////////////////////////////////////
// CCalculCRC message handlers

BSTR CCalculCRC::GetCRC(LPCTSTR cstrFileName) 
{
	CString strResult;

	HANDLE hFile = {NULL};
	DWORD dwSize, bytes_read;

	hFile = CreateFile(cstrFileName, GENERIC_READ, FILE_SHARE_READ | FILE_SHARE_WRITE,
		NULL, OPEN_EXISTING,
		FILE_FLAG_SEQUENTIAL_SCAN, NULL);
	if(hFile == INVALID_HANDLE_VALUE)
	{
		return (BSTR)"Failed to open the file.";
	}

	dwSize = GetFileSize(hFile, NULL);

	CString csData(' ', dwSize);

	ReadFile(hFile, csData.GetBuffer(dwSize), dwSize, &bytes_read, NULL);

	CloseHandle(hFile);

	csData.ReleaseBuffer();

	if(dwSize != bytes_read)
	{
		return (BSTR)"Failed to read the file.";
	}

	int nCRC = Get_CRC(csData, dwSize);

	char ch[20];
	itoa(nCRC, ch, 16);  // Note that the integer is a 16 bit hex

	strResult = ch;

	return strResult.AllocSysString();
}
 
BSTR CCalculCRC::GetCRCForAString(LPCTSTR cstrMessage) 
{
	CString strResult;

	size_t dwSize;

	CString csData;

	csData = cstrMessage;

	dwSize = strlen(cstrMessage);

	int nCRC = Get_CRC(csData, dwSize);

	char ch[20];
	itoa(nCRC, ch, 16);  // Note that the integer is a 16 bit hex

	strResult = ch;

	return strResult.AllocSysString();
}

void CCalculCRC::InitialiserTableCRC32()
{
	ULONG ulPolynomial = 0x04c11db7;

	for(int i = 0; i <= 0xFF; i++)
	{
		crc32_table[i]=Reflect(i, 8) << 24;
		for (int j = 0; j < 8; j++)
			crc32_table[i] = (crc32_table[i] << 1) ^ (crc32_table[i] & (1 << 31) ? ulPolynomial : 0);
		crc32_table[i] = Reflect(crc32_table[i], 32);
	}
}

ULONG CCalculCRC::Reflect(ULONG ref, char ch)
{
	ULONG value(0);

	for(int i = 1; i < (ch + 1); i++)
	{
		if(ref & 1)
			value |= 1 << (ch - i);
		ref >>= 1;
	}
	return value;
}

int CCalculCRC::Get_CRC(CString &csData, DWORD dwSize)
{
	ULONG  crc(0xffffffff);
	int len;
	unsigned char* buffer;

	len = dwSize;
	buffer = (unsigned char*)(LPCTSTR)csData;
	while(len--)
		crc = (crc >> 8) ^ crc32_table[(crc & 0xFF) ^ *buffer++];
	return crc^0xffffffff;
}
