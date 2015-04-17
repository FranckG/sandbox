string os_StringUntilSeparator (string &strString, char chSeparator, char chEscapeChar) {
	int intSeparatorPosition
	int intLength
	int intFirstEscapeCharPosition
	int intSecondEscapeCharPosition
	Buffer bufRetour = create
	Buffer bufString = create
	string strReturnString
	bufString = strString

	intFirstEscapeCharPosition = -1
	intSecondEscapeCharPosition = -1
	if (chEscapeChar != ' ') {
		intFirstEscapeCharPosition = contains(bufString, chEscapeChar)
		if (intFirstEscapeCharPosition > -1) {
			intSecondEscapeCharPosition = contains(bufString, chEscapeChar, intFirstEscapeCharPosition + 1)
		}
	}
	intSeparatorPosition = contains(bufString, chSeparator)
	if (intSecondEscapeCharPosition > intFirstEscapeCharPosition &&
		intSeparatorPosition > intFirstEscapeCharPosition &&
		intSeparatorPosition < intSecondEscapeCharPosition) {
		intSeparatorPosition = contains(bufString, chSeparator, intFirstEscapeCharPosition + 1)
	}

	if (intSeparatorPosition > -1) {
		intLength = length(bufString)
		bufRetour = bufString[0:(intSeparatorPosition - 1)]
		bufString = bufString[(intSeparatorPosition + 1):(intLength)]
		strReturnString = stringOf bufRetour
		strString = stringOf bufString
	} else {
		if (chEscapeChar != ' ' && intFirstEscapeCharPosition > -1) {
			intLength = length(bufString)
			strReturnString = stringOf bufString[1:(intLength - 2)]
			strString = ""
		} else {
			strReturnString = stringOf bufString
			strString = ""
		}
	}
	delete(bufString)
	delete(bufRetour)
	return strReturnString
}


// Transforme une chaine en entier
int os_StringToInt (string strString) {
	int intResult = 0
	int intLength = length(strString)
	int intI
	int intZero = intOf('0')
	int intMultiplicateur = 1
	int intChar

	for (intI=intLength; intI > 0 ; intI--) {
		intChar = intOf(strString[intI-1]) - intZero
		intResult = intResult + (intMultiplicateur * intChar)
		intMultiplicateur = 10 * intMultiplicateur
	}
	return intResult
}

void os_extractPath (string &strExportedFileNamePath, string &strRelativePath, string &strPath) {
	int intPosition = length(strExportedFileNamePath)
    strPath =""
    Stat s
    
    while (intPosition > 0) {
		if (strExportedFileNamePath[intPosition] == '\\') {
			intPosition--
			strPath = strExportedFileNamePath[0:intPosition]..CAnteSlash..strRelativePath
			break
		}
		intPosition--
	}

	s = create strPath
	if (null(s)) {
		mkdir(strPath)
	} else {
		if (!directory(s)) {
			mkdir(strPath)
		}
	}
}

void os_ExtractBaselinePartsFromBaselineName (
	string strBaseline,
	int &intMajor,
	int &intMinor,
	string &strSuffix
) {
	intMajor = null
	intMinor = null
	strSuffix = null
	if (strBaseline != "current") {
		Regexp rxp = regexp "([0-9]*)\\.([0-9]*)( \\((.*)\\)|)"

		if (rxp strBaseline) {
		  s = strBaseline[match 1]
		  intMajor = intOf(realOf(s))
		  s = strBaseline[match 2]
		  intMinor = intOf(realOf(s))
		  strSuffix = strBaseline[match 4]
		}
	}
}