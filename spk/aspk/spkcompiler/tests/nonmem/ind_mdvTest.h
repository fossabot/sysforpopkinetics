/**
 * \file ind_mdvTest.h
 * \brief Tests the NonmemTranslator's ability to handle
 * data sets containing no ID.
 */
#ifndef IND_MDV_TEST_H
#define IND_MDV_TEST_H

#include <cppunit/TestFixture.h>
#include <cppunit/TestSuite.h>

#include <xercesc/dom/DOMDocument.hpp>

class ind_mdvTest : public CppUnit::TestFixture {

  xercesc::DOMDocument *source;
  xercesc::DOMDocument *data;
  xercesc::DOMDocument *report;
  
  bool okToClean;
public:
    static CppUnit::Test * suite();
    virtual void setUp();
    virtual void tearDown();

    void createDataML();
    void createSourceML();
    void parse();
    void testNonmemPars_h();
    void testIndDataClass();
    void testDataSetClass();
    void testPredClass();
    void testDriver();
    void testReportML();
};

#endif