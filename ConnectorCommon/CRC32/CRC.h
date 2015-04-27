// CRC.h : main header file for the CRC DLL
//

#if !defined(AFX_CRC_H__4925D653_807F_4F31_8691_ACADA64AE0AE__INCLUDED_)
#define AFX_CRC_H__4925D653_807F_4F31_8691_ACADA64AE0AE__INCLUDED_

#if _MSC_VER > 1000
#pragma once
#endif // _MSC_VER > 1000

#ifndef __AFXWIN_H__
	#error include 'stdafx.h' before including this file for PCH
#endif

#include "resource.h"		// main symbols

/////////////////////////////////////////////////////////////////////////////
// CCRCApp
// See CRC.cpp for the implementation of this class
//

class CCRCApp : public CWinApp
{
public:
	CCRCApp();

// Overrides
	// ClassWizard generated virtual function overrides
	//{{AFX_VIRTUAL(CCRCApp)
	public:
	virtual BOOL InitInstance();
	//}}AFX_VIRTUAL

	//{{AFX_MSG(CCRCApp)
		// NOTE - the ClassWizard will add and remove member functions here.
		//    DO NOT EDIT what you see in these blocks of generated code !
	//}}AFX_MSG
	DECLARE_MESSAGE_MAP()
};


/////////////////////////////////////////////////////////////////////////////

//{{AFX_INSERT_LOCATION}}
// Microsoft Visual C++ will insert additional declarations immediately before the previous line.

#endif // !defined(AFX_CRC_H__4925D653_807F_4F31_8691_ACADA64AE0AE__INCLUDED_)
