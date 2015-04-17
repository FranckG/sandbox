OleAutoObj oav_mclsArtifactsVersion

void InitializeArtifactsVersion () {
	oav_mclsArtifactsVersion = oleCreateAutoObject("ArtifactVersion.Artifact")
}

void TerminateArtifactsVersion () {
	oleCloseAutoObject(oav_mclsArtifactsVersion)
}

void oav_getFileHash (
	string &sFileName,
	string &sHash
) {
	OleAutoArgs autoArgs = create
	put(autoArgs, sFileName)
	oleMethod(oav_mclsArtifactsVersion, "GetFileHash", autoArgs, sHash)
	delete(autoArgs)
}


void oav_getHash (
	Buffer bufString,
	string &sHash
) {
	OleAutoArgs autoArgs = create
	put(autoArgs, tempStringOf(bufString))
	oleMethod(oav_mclsArtifactsVersion, "GetHash", autoArgs, sHash)
	delete(autoArgs)
}



