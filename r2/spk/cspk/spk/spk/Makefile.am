# Static libraries
lib_LIBRARIES   = libspk.a

INST_BASE     = /usr/local
libdir        = $(INST_BASE)/lib/spktest
pkgincludedir = $(INST_BASE)/include/spktest/spk

# Set C pre-processor flags
AM_CPPFLAGS = \
	-I. \
	-I$(top_builddir) \
	-I$(INST_BASE)/include/spktest \
	-I/usr/share/pvm3/include

# Set C++ compiler flags
if SPK_RELEASE
AM_CXXFLAGS = -O3 -Dspk_release -DNDEBUG
else
AM_CXXFLAGS = -g  -Dspk_debug
endif
if SPK_PROFILE
AM_CXXFLAGS += -pg -Dspk_profiling
endif

# Set F77 compiler flags
AM_FFLAGS = -g

libspk_a_SOURCES = \
	ABA_x.cpp \
	add.cpp \
	AkronBtimesC.cpp \
	AkronItimesC.cpp \
	allTrue.cpp \
	allZero.cpp \
	backDiv.cpp \
	BlockAlloc.cpp \
	blockDiagonalDerivative.cpp \
	broadCastEndOfSpk.cpp \
	c2dsyev.f \
	calcMean.cpp \
	centdiff.cpp \
	Channel.cpp \
	cholesky.cpp \
	countTrues.cpp \
	Covariance.cpp \
	derParStatistics.cpp \
	det.cpp \
	DirBasedParallelControls.cpp \
	divByScalar.cpp \
	DoubleMatrix.cpp \
	elementwiseAnd.cpp \
	elementwiseOr.cpp \
	elsq.cpp \
	elsq_xBlockDiag.cpp \
	elsq_x.cpp \
	elsq_xDiag.cpp \
	doubleToScalar.cpp \
	doubleToScalarArray.cpp \
	EqIndModel.cpp \
	estimateB.cpp \
	expectedHessian.cpp \
	extractIndex.cpp \
	File.cpp \
	firstOrderOpt.cpp \
	fitIndividual.cpp \
	fitPopulation.cpp \
	FpErrorChecker.cpp \
	FullCovariance.cpp \
	FullDataCovariance.cpp \
	Function.cpp \
	FullIndParCovariance.cpp \
	getCol.cpp \
	getMulCols.cpp \
	getMulRows.cpp \
	getRow.cpp \
	getSubblock.cpp \
	hasUnnormNumber.cpp \
	identity.cpp \
	IkronBtimesC.cpp \
	IndInputDataPackage.cpp \
	indOptPvm.cpp \
	IndOutputDataPackage.cpp \
	indResiduals.cpp \
	IndResults.cpp \
	indStatistics.cpp \
	IndVars.cpp \
	inverse.cpp \
	inxToMax.cpp \
	intToOrdinalString.cpp \
	isDmatEpsEqual.cpp \
	isEndOfSpk.cpp \
	isGreaterThanOrEqualTo.cpp \
	isLessThanOrEqualTo.cpp \
	isNotANumber.cpp \
	isSymmetric.cpp \
	isUnnormNumber.cpp \
	lambda.cpp \
	lambdaDiff.cpp \
	lambda2diff.cpp \
	lTilde.cpp \
	lTildePvm.cpp \
	mapObj.cpp \
	mapObjDiff.cpp \
	mapOpt.cpp \
	mapTilde.cpp \
	matabs.cpp \
	matmax.cpp \
	matmin.cpp \
	mulByScalar.cpp \
	multiply.cpp \
	NaiveFoModel.cpp \
	node.cpp \
	norm.cpp  \
	Optimizer.cpp \
	placeRows.cpp \
	PopConstVals.cpp \
	popResiduals.cpp \
	popStatistics.cpp \
	PopVars.cpp \
	ppkaOpt.cpp \
	ppkaOptParallel.cpp \
	QN01BoxError.cpp \
	quasiNewtonAnyBox.cpp \
	randNormal.cpp \
	replaceIth.cpp \
	replaceJth.cpp \
	replaceSubblock.cpp \
	wres.cpp \
	rvecInverse.cpp \
	scalarToDouble.cpp \
	scalarToDoubleArray.cpp \
	simulate.cpp \
	spkpvm.cpp \
	spk_non_par.cpp \
	SpkError.cpp \
	SpkException.cpp \
	SpkModel.cpp \
	SpkWarning.cpp \
	statistics.cpp \
	StatusList.cpp \
	subtract.cpp \
	symmetrize.cpp \
	System.cpp \
	transpose.cpp \
	transposeDerivative.cpp \
	transposeRowBlocks.cpp \
	twoStageMethod.cpp \
	UTranTimesSymKronSymTimesU.cpp \
	UTranTimesSymKronSymTimesU_x.cpp \
	WarningsManager.cpp \
	ABA_x.h \
	add.h \
	AkronBtimesC.h \
	AkronItimesC.h \
	allTrue.h \
	allZero.h \
	backDiv.h \
	BlockAlloc.h \
	blockDiagonalDerivative.h \
	broadCastEndOfSpk.h \
	c2dsyev.h \
	calcMean.h \
	centdiff.h \
	Channel.h \
	cholesky.h \
	countTrues.h \
	Covariance.h \
	DBL_EPS_EQUAL_MULT.h \
	derParStatistics.h \
	det.h \
	DoubleMatrix.h \
	divByScalar.h \
	DirBasedParallelControls.h \
	elementwiseAnd.h \
	elementwiseOr.h \
	elsq.h \
	elsq_x.h \
	elsq_xBlockDiag.h \
	elsq_xDiag.h \
	doubleToScalar.h \
	doubleToScalarArray.h \
	EqIndModel.h \
	estimateB.h \
	expectedHessian.h \
	extractIndex.h \
	File.h \
	firstOrderOpt.h \
	fitIndividual.h \
	fitPopulation.h \
	FpErrorChecker.h \
	FullCovariance.h \
	FullDataCovariance.h \
	FullIndParCovariance.h \
	Function.h \
	getCol.h \
	getMulCols.h \
	getMulRows.h \
	getRow.h \
	getSubblock.h \
	hasUnnormNumber.h \
	identity.h \
	IkronBtimesC.h \
	IndDataPackage.h \
	indOptPvm.h \
	indResiduals.h \
	IndResults.h \
	indStatistics.h \
	IndVars.h \
	inverse.h \
	inxToMax.h \
	intToOrdinalString.h \
	isDblEpsEqual.h \
	isDmatEpsEqual.h \
	isEndOfSpk.h \
	isGreaterThanOrEqualTo.h \
	isLessThanOrEqualTo.h \
	isNotANumber.h \
	isSymmetric.h \
	isUnnormNumber.h \
	lambda.h \
	lambdaDiff.h \
	lambda2diff.h \
        linearInterpolate.h \
	mapObjDiff.h \
	mapObj.h \
	mapOpt.h \
	mapTilde.h \
	matmax.h \
	matabs.h \
	matmin.h \
	mulByScalar.h \
	multiply.h \
	namespace_population_analysis.h \
	NaiveFoModel.h \
	node.h \
	norm.h \
	Optimizer.h \
	Objective.h \
	lTilde.h \
	lTildePvm.h \
	ParallelControls.h \
	PARALLEL_FILE_CONSTS.h \
	pi.h \
	placeRows.h \
	PopConstVals.h \
	popResiduals.h \
	popStatistics.h \
	PopVars.h \
	ppkaOpt.h \
	printInMatrix.h \
	quasiNewtonAnyBox.h \
	QuasiNewtonAnyBoxObj.h \
	randNormal.h \
	replaceIth.h \
	replaceJth.h \
	replaceSubblock.h \
	wres.h \
	rvec.h \
	rvecInverse.h \
	scalarToDouble.h \
	scalarToDoubleArray.h \
	simulate.h \
	spk_non_par.h \
	Spk.h \
	SpkError.h \
	SpkException.h \
	SpkModel.h \
	spkpvm.h \
	SpkValarray.h \
	SpkWarning.h \
	statistics.h \
	StatusList.h \
	subtract.h \
	symmetrize.h \
	System.h \
	transpose.h \
	transposeDerivative.h \
	transposeRowBlocks.h \
	twoStageMethod.h \
	UTranTimesSymKronSymTimesU.h \
	UTranTimesSymKronSymTimesU_x.h \
	WarningsManager.h
 

pkginclude_HEADERS = \
	$(top_builddir)/config.h \
	ABA_x.h \
	add.h \
	AkronBtimesC.h \
	AkronItimesC.h \
	allTrue.h \
	allZero.h \
	backDiv.h \
	BlockAlloc.h \
	blockDiagonalDerivative.h \
	broadCastEndOfSpk.h \
	c2dsyev.h \
	calcMean.h \
	centdiff.h \
	Channel.h \
	cholesky.h \
	countTrues.h \
	Covariance.h \
	DBL_EPS_EQUAL_MULT.h \
	derParStatistics.h \
	det.h \
	divByScalar.h \
	DirBasedParallelControls.h \
	DoubleMatrix.h \
	elementwiseAnd.h \
	elementwiseOr.h \
	elsq.h \
	elsq_x.h \
	elsq_xBlockDiag.h \
	elsq_xDiag.h \
	doubleToScalar.h \
	doubleToScalarArray.h \
	EqIndModel.h \
	estimateB.h \
	expectedHessian.h \
	extractIndex.h \
	File.h \
	firstOrderOpt.h \
	fitIndividual.h \
	fitPopulation.h \
	FpErrorChecker.h \
	FullCovariance.h \
	FullDataCovariance.h \
	FullIndParCovariance.h \
	Function.h \
	getCol.h \
	getMulCols.h \
	getMulRows.h \
	getRow.h \
	getSubblock.h \
	hasUnnormNumber.h \
	identity.h \
	IkronBtimesC.h \
	IndDataPackage.h \
	indOptPvm.h \
	indResiduals.h \
	IndResults.h \
	indStatistics.h \
	IndVars.h \
	inverse.h \
	inxToMax.h \
	intToOrdinalString.h \
	isDblEpsEqual.h \
	isDmatEpsEqual.h \
	isEndOfSpk.h \
	isGreaterThanOrEqualTo.h \
	isLessThanOrEqualTo.h \
	isNotANumber.h \
	isSymmetric.h \
	isUnnormNumber.h \
	lTilde.h \
	lTildePvm.h \
	lambda.h \
	lambdaDiff.h \
	lambda2diff.h \
        linearInterpolate.h \
	mapObjDiff.h \
	mapObj.h \
	mapOpt.h \
	mapTilde.h \
	matmax.h \
	matabs.h \
	matmin.h \
	mulByScalar.h \
	multiply.h \
	NaiveFoModel.h \
	namespace_population_analysis.h \
	node.h \
	norm.h \
	Objective.h \
	Optimizer.h \
	ParallelControls.h \
	PARALLEL_FILE_CONSTS.h \
	pi.h \
	placeRows.h \
	PopConstVals.h \
	popResiduals.h \
	popStatistics.h \
	PopVars.h \
	ppkaOpt.h \
	printInMatrix.h \
	quasiNewtonAnyBox.h \
	QuasiNewtonAnyBoxObj.h \
	randNormal.h \
	replaceIth.h \
	replaceJth.h \
	replaceSubblock.h \
	wres.h \
	rvec.h \
	rvecInverse.h \
	scalarToDouble.h \
	scalarToDoubleArray.h \
	simulate.h \
	spk_non_par.h \
	Spk.h \
	SpkError.h \
	SpkException.h \
	SpkModel.h \
	spkpvm.h \
	SpkValarray.h \
	SpkWarning.h \
	statistics.h \
	StatusList.h \
	subtract.h \
	symmetrize.h \
	System.h \
	transpose.h \
	transposeDerivative.h \
	transposeRowBlocks.h \
	twoStageMethod.h \
	UTranTimesSymKronSymTimesU.h \
	UTranTimesSymKronSymTimesU_x.h \
	WarningsManager.h

