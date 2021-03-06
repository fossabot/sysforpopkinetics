/*
%************************************************************************
%                                                                       *
%  From:   Resource Facility for Population Kinetics                    *
%          Department of Bioengineering Box 352255                      *
%          University of Washington                                     *
%          Seattle, WA 98195-2255                                       *
%                                                                       *
%  Copyright (C) 2002, University of Washington,                        *
%  Resource Facility for Population Kinetics. All Rights Reserved.      *
%                                                                       *
%  This software was developed with support from NIH grant RR-12609.    *
%  Please cite this grant in any publication for which this software    *
%  is used and send a notification to the address given above.          *
%                                                                       *
%  Check for updates and notices at:                                    *
%  http://www.rfpk.washington.edu                                       *
%                                                                       *
%************************************************************************

*/
/*************************************************************************
 *
 * File: addTest.h
 *
 *
 * Declare addTest class.
 *
 * Author: Sachiko Honda
 *
 *************************************************************************/
#ifndef ADD_TEST_H
#define ADD_TEST_H

#include <iostream>
#include <string>

#include <cppunit/TestFixture.h>
#include <cppunit/Test.h>

#include <vector>
#include "../../../spk/DoubleMatrix.h"

class addTest : public CppUnit::TestFixture
{
    int _n;
    std::vector<int> _rows, _cols;
    std::vector<DoubleMatrix*> _As, _Bs, _negAs, _Ss;

public: 
    static CppUnit::Test* suite();
    void setUp();
    void tearDown();

    // add test cases as void member functions
    void testAddVal();
    void testAddRef();
};

#endif
