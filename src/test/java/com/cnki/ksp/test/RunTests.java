package com.cnki.ksp.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.cnki.ksp.test.conn.TestArticle;
import com.cnki.ksp.test.conn.TestCaptureRecord;
import com.cnki.ksp.test.jsoap.JXDocumentTest;
import com.cnki.ksp.test.jsoap.TestForward;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TestArticle.class, TestForward.class, TestCaptureRecord.class, JXDocumentTest.class })
public class RunTests {

	public static final int TEST_DELETE = 3;

}
