/*
$begin MapMonte$$
$spell
	Eval
	Spk
	std
	const
	valarray
	Bayes
$$

$index integral, Map Bayes$$
$index Map Bayes, integral$$
$index Bayes, Map integral$$
$index individual, integral$$

$section Monte Carlo Integration of Map Bayesian Objective$$

$table
$bold Syntax$$ $cnext
$syntax%void MapMonte(
	SpkModel                    &%model%           ,
	const std::valarray<int>    &%N%               , 
	const std::valarray<double> &%y%               ,
	const std::valarray<double> &%alpha%           ,
	const std::valarray<double> &%L%               ,
	const std::valarray<double> &%U%               ,
	size_t                       %individual%      ,
	size_t                       %numberEval%      ,
	//
	double                      &%integralEstimate%,
	double                      &%estimateStd%
)%$$
$tend

$fend 25$$

$head Description$$
Monte-Carlo integration of the 
$xref/MapBay//Map Bayesian/$$ probability density
function corresponding to one individual of a population; i.e.,
$latex \[
	\int_L^U \exp [ - G_i (b) ] \D  b
\] $$
the function $latex G_i (b)$$ is defined by 
$latex \[
\begin{array}{rcl}
G_i ( b ) & = &
\frac{1}{2} \log \det [ 2  \pi  D( \alpha ) ]
+
\frac{1}{2} b^T D( \alpha )^{-1} b
+
\frac{1}{2} \log \det [ 2  \pi  R_i ( b , \alpha ) ]
\\
& + &
\frac{1}{2} 
[ y_i - f_i ( b , \alpha )]^T 
	R_i ( b , \alpha )^{-1} 
		[ y_i - f_i ( b , \alpha )] 
\end{array}
\] $$
where $latex i$$ is the index corresponding to this individual,
$latex D ( \alpha )$$ is the covariance of the random effects,
$latex f_i ( b , \alpha )$$ is the mean, given the fixed and random effects,
of this individuals measurements, and
$latex R_i ( b , \alpha )$$ is the variance, given the fixed and random effects,
of this individuals measurements.

$head model$$
is a $xref/MonteSpkModel//Monte-Carlo model/$$ 
object that represents this population model.
This will be used to evaluate the model functions
$latex D ( \alpha )$$, $latex R_i ( b , \alpha )$$ and 
$latex f_i ( b , \alpha )$$
where $italic i$$ is equal to the argument $italic individual$$.


$head N$$
contains the number of measurements for each
individual in the population.

$head y$$
contains the data for all the individuals in the population.
It length must be equal to the sum of the elements in $italic N$$.

$head alpha$$
value of the fixed effects.

$head L$$
lower limits for the value of the random effects.

$head U$$
upper limits for the value of the random effects.

$head individual$$
index corresponding to this individual.

$head numberEval$$
number of evaluations of the 
$xref/MapBay//Map Bayesian objective/$$ 
that will be used for the approximation.
Each evaluation will correspond to a different
value for the random effects; i.e., $latex b$$
in the functions $latex f_i (b , \alpha )$$ and 
$latex R_i (b, \alpha )$$.
The value of $latex \alpha$$ will be the
same for all evaluations.

$head integralEstimate$$
The input value of $italic integralEstimate$$ does not matter.
Its output value
is an approximation for the integral.
This is an approximately normal random variable with 
mean equal to the integral and standard deviation
equal to $italic estimateStd$$.

$head estimateStd$$
The input value of $italic estimateStd$$ does not matter.
Its output value
is an approximation for the standard deviation of $italic estimateIntegral$$.

$end

*/
# include <valarray>
# include <cassert>
# include <spk/SpkModel.h>

# include "MapBay.h"
# include "MapMonte.h"

#include <stdlib.h>
#include <gsl/gsl_math.h>
#include <gsl/gsl_monte.h>
#include <gsl/gsl_monte_plain.h>

# define MapMonteDebug 0

namespace {
	// begin empty namespace
	extern "C" double ExpNegMapBay(double *x, size_t dim, void *params)
	{	return exp( - MapBay(x, dim, params) ); }
}

void MapMonte(
	SpkModel                    &model           ,
	const std::valarray<int>    &N               , 
	const std::valarray<double> &y               ,
	const std::valarray<double> &alpha           ,
	const std::valarray<double> &L               ,
	const std::valarray<double> &U               ,
	size_t                       individual      ,
	size_t                       numberEval      ,
	//
	double                      &integralEstimate,
	double                      &estimateStd     ) 
{
	// number of random effects
	assert( L.size() ==  U.size() );
	size_t numberRandomEffects = L.size();

	// check for valid individual index
	assert( individual < N.size() );

	// compute and check for valid data offset for this individual
	size_t offset = 0;
	size_t i;
	for( i = 0; i < individual; i++ )
	{	assert( N[i] >= 0 );
		offset += N[i];
	}
	assert( offset + N[individual] <= y.size() );

	// extrace data for this individual
	std::valarray<double> yi = y[ std::slice(offset, N[individual], 1) ];

	// pseudo constructor for this case (uses all the data)
	MapBaySet(&model, yi, alpha, individual, numberRandomEffects);

	// type of Gsl random number generator
	const gsl_rng_type *rngType = gsl_rng_default;

	// range for Gsl random number generator
	gsl_rng *rngRange = gsl_rng_alloc(rngType);

	// set the seed
	unsigned long int seed = 123;
 	gsl_rng_set (rngRange, seed);

	// function information
	void *Null = 0;
	gsl_monte_function Integrand = 
		{ &ExpNegMapBay, numberRandomEffects, Null };

	// default random set up
	gsl_rng_env_setup();

	// very simple monte carlo integration
	gsl_monte_plain_state *state = 
		gsl_monte_plain_alloc (numberRandomEffects);

	// integrate over the region defined by L, U
	double *Lower = new double[ numberRandomEffects ];
	double *Mid   = new double[ numberRandomEffects ];
	double *Upper = new double[ numberRandomEffects ];
	size_t j;
	for(j = 0; j < numberRandomEffects; j++)
	{	Lower[j] = L[j];
		Mid[j]   = .5 * (L[j] + U[j]);
		Upper[j] = U[j];
	}
# if MapMonteDebug
	double mapBay = MapBay(Mid, numberRandomEffects, Null);
	std::cout << "MapMonte: individual = " << individual;
	std::cout << ", MapBay(.5*(U + L)) = " << mapBay;
	std::cout << ", y_i = ";
	for(j = 0; j < yi.size(); j++)
	{	std::cout << yi[j];
		if( j + 1 < yi.size() )
			std::cout << ", ";
	} 
	std::cout << std::endl;
# endif
	gsl_monte_plain_integrate(
		&Integrand                    , 
		Lower                         , 
		Upper                         , 
		numberRandomEffects           , 
		numberEval                    , 
		rngRange                      , 
		state                         , 
		&integralEstimate             , 
		&estimateStd
	);
	delete [] Lower;
	delete [] Mid;
	delete [] Upper;
}

# undef MapMonteDebug
