/*
-----------------------------------------------------------------------
From:   Resource Facility for Population Kinetics
          Department of Bioengineering Box 352255
          University of Washington
          Seattle, WA 98195-2255

This file is part of the System for Population Kinetics (SPK), which
was developed with support from NIH grants RR-12609 and P41-
EB001975. Please cite these grants in any publication for which this
software is used and send a notification to the address given above.

SPK is Copyright (C) 1998-2003, by the University of Washington,
Resource Facility for Population Kinetics, and is made available as
free open source software under the terms of the University of
Washington Free-Fork License as a public service.  A copy of the
License can be found in the COPYING file in the root directory of this
distribution.
-----------------------------------------------------------------------
Software:   Brad Bell (brad@apl.washington.edu)
Mathematics: Brad Bell & Jim Burke (burke@math.washington.edu)

$begin  Delta$$
$spell
	CppAD
	namespace
	iostream
	bool
	std
	cout
	endl
	dtmp
	Qxi
	Qdxi
	fabs
	sqrt
	complementarity
	const
	da
$$

$mindex first order approximation step$$
$section First Order Approximation Step$$

$table
$bold Syntax$$ $cnext
$syntax%
const char * Delta(
	// Input Arguments
	size_t %n%,
	double %alpha%,
	const double *%Q%,  // length n * n
	const double *%r%,  // length n
	const double *%l%,  // ...
	const double *%u%,  // ...
	const double *%x%,  // ...
	const double *%a%,  // ...
	const double *%b%,  // ...
	// Output Arguments
	double *%dx%,       // length n 
	double *%da%,       // ...
	double *%db%)       // ...
%$$
$tend

$fend 20$$

$head Exceptions$$
The routine $code Delta$$ is $xref/glossary/Exception Safe/exception safe/$$.

$head Return Value$$
If the return value of $code Delta$$ is either the (const char *) "ok",
it succeeded in meeting its specifications.
Otherwise, the return value is an error message and the specifications
have not been met.
Provided that all the input and output arguments have their specified lengths,
such a failure should not have any side effects.

$head Input Arguments$$
We define 
$latex n \in \Z_+$$,
$latex \alpha \in \R_+$$,
$latex Q \in \R^{n \times n}$$,
$latex r \in \R^n$$,
$latex l \in \R^n$$,
$latex u \in \R^n$$,
$latex x \in \R^n$$,
$latex a \in \R^n$$,
$latex b \in \R^n$$,
as the values of the corresponding input arguments.
The matrix $latex Q$$ must be positive definite
$latex 0 < \alpha < 1$$,
$latex l < x < u$$,
$latex 0 < a $$, and
$latex 0 < b $$.

$head Notation$$
We use $latex e \in \R^n$$ for the
$xref/glossary/e: Vector of Ones/vector of ones/$$.
We use $latex D(x)$$ for the
$xref/glossary/D: Diagonal Matrix of a Vector/diagonal matrix of a vector/$$.
We use $latex R( x , a , b )$$ for the
$xref/glossary/Residual Function/residual function/$$.

This routine solves for 
$latex \Delta x \in \R^n$$, 
$latex \Delta a \in \R^n$$,
and
$latex \Delta b \in \R^n$$
such that:
$latex \[
R( x , a , b ) + 
R^{(1)} (x, a, b)
\left( \begin{array}{c}
	\Delta x \\
	\Delta a \\
	\Delta b
\end{array} \right)
=
\alpha
\left( \begin{array}{c}
avg [ R ( x , a , b ) ] e
\\
avg [ R ( x , a , b ) ] e
\\
0
\end{array} \right)
\]$$
where $latex R^{(1)}$$ is the derivative of $latex R$$
and $latex avg$$ is the 
$cref/Euclidean Average/glossary/avg: Euclidean Average/$$.

$head Output Arguments$$
The values $latex \Delta x$$, $latex \Delta a$$, and $latex \Delta b$$
will be placed in the output arguments
$latex dx$$, $latex da$$, and $latex db$$
respectively.
These arguments must be vectors of length $latex n$$.


$head Theory$$
The derivative of $latex R$$ is given by
$latex \[
R^{(1)} (x, a, b)
=
\left( \begin{array}{ccc}
	- D(b)  & 0        & D(u - x) \\
	D(a)    & D(x - l) &  0       \\
	Q       & - D(e)   &  D(e)
\end{array} \right)
\] $$

Substituting in for $latex R$$ and its derivative
in the equation defining 
$latex \Delta x$$
$latex \Delta a$$, and
$latex \Delta b$$
we obtain:
$latex \[
\left( \begin{array}{ccc}
	-D( b )  & 0        & D( u - x ) \\
	D( a )   & D( x - l) &  0        \\
	Q      & - D(e)   &  D(e) 
\end{array} \right)
\left( \begin{array}{c}
	 \Delta x  \\
	 \Delta a  \\
	 \Delta b 
\end{array} \right)
=
\left( \begin{array}{c}
	\alpha \beta e - D(u - x) b  \\
	\alpha \beta e - D(x - l) a \\
	- Q x - r  + a - b
\end{array} \right)
\] $$
where
$latex \[
\begin{array}{rcl}
	\beta & = & \{ avg [ D( u - x ) b ] + avg[ D(x - 1) a ] \} / 2
\end{array}
\] $$
Replacing the first row by
the first row times $latex  - D(u - x)^{-1}$$,
and replacing the second row by
the second row times $latex D(x - l)^{-1}$$,
we obtain:
$latex \[
\left( \begin{array}{ccc}
	D( u - x )^{-1} D( b )  & 0       & -D(e) \\
	D( x - l )^{-1} D( a )  & D(e)    &  0    \\
	Q                       & - D(e)  &  D(e) 
\end{array} \right)
\left( \begin{array}{c}
	 \Delta x  \\
	 \Delta a  \\
	 \Delta b 
\end{array} \right)
 =
\left( \begin{array}{c}
	b - \alpha \beta D( u - x )^{-1} e  \\
	\alpha \beta D( x - l )^{-1} e - a \\
	- Q x - r  + a - b
\end{array} \right)
\] $$
Replacing the first row by
the first row plus the second row plus the third row,
we obtain:
$latex \[
\begin{array}{rcl}
\Delta x 
& = &
\left[ D( u - x )^{-1} D( b ) + D( x - l )^{-1} D( a )  + Q \right]^{-1}
\\
& \times &
\left[ 
\alpha \beta D( x - l )^{-1} e 
- \alpha \beta D( u - x )^{-1} e  
- Q x - r
\right]
\\
\Delta a
& = &
\alpha \beta D( x - l )^{-1} e - a
-
D( x - l )^{-1} D( a ) \Delta x
\\
\\
\Delta b
& = &
- Q x - r  + a - b
- Q \Delta x
+ \Delta a
\end{array}
\] $$

$head Example$$
The following example is also a test.
It returns true if it succeeds and false otherwise.
$codep */

# include <QN01Box/Delta.h>
# include <cppad/NearEqual.h>
# include <QN01Box/Memory.h>


# include <iostream>
# include <math.h>
# include <string>

bool DeltaTest(std::string &msg)
{	bool ok = true;
	using namespace QN01Box;

	size_t  n     = 3;
	double  alpha = .25;
	const double  Q[]   = {
		  1., -.5, .25,
		-.5,   1., -.5,
		 .25,  .5, 1.
	};
	const double  r[]   = {1., 2., 3.};
	const double  l[]   = {-1., -2., -1.};
	const double  u[]   = {3., 2., 3.};
	const double  x[]   = {.1, .2 , .3 };
	const double  a[]   = {1., 3., 2.};
	const double  b[]   = {2., 1., 2.};
	double dx[3];
	double da[3];
	double db[3];

	msg = Delta(n, alpha, Q, r, l, u, x, a, b, dx, da, db);
	if( msg == "ok" )
		msg = "";
	else	ok = false;

	// determine beta 
	double beta  = 0.;
	double dtmp;
	size_t i;
	for(i = 0; i < n; i++)
	{	dtmp = (u[i] - x[i])* b[i];
		beta += dtmp;
		dtmp = (x[i] - l[i])* a[i];
		beta += dtmp;
	}
	beta  = beta / (2 * n);

	// check solution of equations
	for(i = 0; i < n; i++)
	{	double u_x = u[i] - x[i];
		double x_l = x[i] - l[i];
		// D(u-x) * b - D(b) * dx + D(u-x) * db = alpha * beta * e
		ok &= CppAD::NearEqual( u_x*b[i] - b[i]*dx[i] + u_x*db[i], 
			alpha * beta, 1e-12, 1e-12 );

		// D(x-l) * a + D(a) * dx + D(x-l) * da = alpha * beta * e
		ok &= CppAD::NearEqual( x_l*a[i] + a[i]*dx[i] + x_l*da[i], 
			alpha * beta , 1e-12 , 1e-12 ); 

		// Q * x + r - a + b + Q * dx - D(e) * da + D(e) * db  = 0 
		double Qxi = 0.;
		double Qdxi = 0.;
		size_t j;
		for(j = 0; j < n; j++)
		{	Qxi  += Q[i*n+j] * x[j];
			Qdxi += Q[i*n+j] * dx[j];
		}
		ok &= CppAD::NearEqual(0., 
			Qxi + r[i] - a[i] + b[i] + Qdxi - da[i] + db[i],
			1e-12, 1e-12 );
	}

	return ok;
}
/* $$
$end
------------------------------------------------------------------------------
*/

# include <stdlib.h>
# include <cppad/CppAD_vector.h>
# include <cppad/LuSolve.h>
# include <QN01Box/MaxAbs.h>


namespace QN01Box {
const char * Delta(
	size_t n,
	double alpha,
	const double *Q,  // length n * n
	const double *r,  // length n
	const double *l,  // ...
	const double *u,  // ...
	const double *x,  // ...
	const double *a,  // ...
	const double *b,  // ...
	double *dx,    // ... 
	double *da,    // ...
	double *db)    // ...
{	// indices
	size_t i;

	// LuSolve expects SimpleVector arguments
	CppAD::vector<double> Qplus(n * n);
	CppAD::vector<double> rhs(n);
	CppAD::vector<double> x_l(n);
	CppAD::vector<double> u_x(n);
	CppAD::vector<double> result(n);

	// u_x = u - x
	// x_l = x - l
	double sum  = 0.;
	double dtmp;
	for(i = 0; i < n; i++)
	{	u_x[i]    = u[i] - x[i];
		dtmp     = u_x[i] * b[i];
		QN01BoxUnknownError(dtmp > 0., "Delta")
		sum     += dtmp;

		x_l[i]   = x[i] - l[i];
		dtmp     = x_l[i] * a[i];
		QN01BoxUnknownError(dtmp > 0., "Delta")
		sum     += dtmp;
	}
	double beta = sum / (double) (2 * n);

	// Qplus = Q + D(u - x)^-1 * b + D(x - l)^-1 * a
	// rhs   = alpha * beta * (x - l)^-1 - alpha * beta * (u - x)^-1 
	//       - Q * x - r  
	for(i = 0; i < n; i++)
	{	// Qxi        = ith element of Q * x
		// Qplus(i,:) = Q(i,:)
		double Qxi = 0;
		size_t j;
		for(j = 0; j < n; j++)
		{	
			Qplus[i * n + j] = Q[i * n + j];
			Qxi             += Q[i * n + j] * x[j];
		}

		// add terms to diagonal of Qplus
		// and compute rhs(i)
		Qplus[i * n + i] += b[i] / u_x[i] + a[i] / x_l[i];
		rhs[i] = alpha * beta / x_l[i] - alpha * beta  / u_x[i] 
		       - Qxi - r[i];
	}
	// dx = solution of the eqution Qplus * dx = rhs
	// (note, Qplus is symmetric positive definate)
	size_t m    = 1; // number of columns in rhs and dx
	double logabsdet;
	double sign = CppAD::LuSolve(n, m, Qplus, rhs, result, logabsdet);

	// copy result into return value
	for(i = 0; i < n; i++)
		dx[i] = result[i];

	if( sign == 0. )
		return (const char *)("Delta: cannot solve equations for Delta x");
	
	// da   = alpha * beta * (x - l)^-1 - a - D(x - l)^-1 * D(a) * dx
	// db   = - Q * x - r + a - b - Q * dx + da
	for(i = 0; i < n; i++)
	{	// Qxi        = ith element of Q * x
		// Qdxi       = ith eleemnt of Q * dx
		double Qdxi = 0.;
		double Qxi  = 0.;
		size_t j;
		for(j = 0; j < n; j++)
		{	
			Qxi  += Q[i * n + j] *  x[j];
			Qdxi += Q[i * n + j] * dx[j];
		}
		da[i] = alpha * beta / x_l[i] - a[i] - a[i] * dx[i] / x_l[i];
		db[i] = - Qxi - r[i] + a[i] - b[i] - Qdxi + da[i];
	}
# ifndef NDEBUG
	// check linear solution
	size_t j;
	bool ok;
	double left, right;
	double abserr = 1e-10 * ( MaxAbs(n * n, Q) + MaxAbs(n, r) );
	for(i = 0; i < n; i++)
	{	// -D(b)*dx + D(u-x)*db = alpha*beta*e - D(u-x)*b
		left  = -b[i]*dx[i] + (u[i]-x[i])*db[i];
		right = alpha*beta - (u[i]-x[i])*b[i];
		ok    = CppAD::NearEqual(left, right, 1e-10, abserr);
		QN01BoxUnknownError(ok, "Delta")
		//
		// D(a)*dx + D(x-l)*da = alpha*beta*e - D(x-l)*a
		left  = a[i]*dx[i] + (x[i]-l[i])*da[i];
		right = alpha*beta - (x[i]-l[i])*a[i];
		ok    = CppAD::NearEqual(left, right, 1e-10, abserr);
		QN01BoxUnknownError(ok, "Delta")
		//
		// Q*dx - D(e)*da + D(e)*db = -Q*x - r + a - b
		left  = 0.;
		right = 0.;
		for(j = 0; j < n; j++)
		{	left  += Q[i * n + j] * dx[j];	
			right -= Q[i * n + j] * x[j];	
		}
		left  += - da[i] + db[i];
		right += - r[i] + a[i] - b[i];
		ok    = CppAD::NearEqual(left, right, 1e-10, abserr);
		QN01BoxUnknownError(ok, "Delta")
	}
# endif
	return (const char *)("ok");
}
} // End QN10Box namespace
