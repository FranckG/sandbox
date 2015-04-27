#if !defined(AFX_CALCULCRC_H__094707D5_894E_45D9_91E1_DDA0E5C63DB6__INCLUDED_)
#define AFX_CALCULCRC_H__094707D5_894E_45D9_91E1_DDA0E5C63DB6__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000
// CalculCRC.h : header file
//



/////////////////////////////////////////////////////////////////////////////
// CCalculCRC command target

class CCalculCRC : public CCmdTarget
{
	DECLARE_DYNCREATE(CCalculCRC)

	CCalculCRC();           // protected constructor used by dynamic creation

// Attributes
public:

// Operations
public:

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCalculCRC)
	public:
	virtual void OnFinalRelease();
	//}}AFX_VIRTUAL

// Implementation
protected:
	ULONG crc32_table[256];
	void InitialiserTableCRC32();
	ULONG Reflect(ULONG ref, char ch);
	int Get_CRC(CString& csData, DWORD dwSize);

	virtual ~CCalculCRC();

	// Generated message map functions
	//{{AFX_MSG(CCalculCRC)
		// NOTE - the ClassWizard will add and remove member functions here.
	//}}AFX_MSG

	DECLARE_MESSAGE_MAP()
	DECLARE_OLECREATE(CCalculCRC)

	// Generated OLE dispatch map functions
	//{{AFX_DISPATCH(CCalculCRC)
	afx_msg BSTR GetCRC(LPCTSTR cstrFileName);
	afx_msg BSTR GetCRCForAString(LPCTSTR cstrMessage);
	//}}AFX_DISPATCH
	DECLARE_DISPATCH_MAP()
	DECLARE_INTERFACE_MAP()
};

/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CALCULCRC_H__094707D5_894E_45D9_91E1_DDA0E5C63DB6__INCLUDED_)
