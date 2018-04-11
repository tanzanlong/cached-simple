package service.base;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Created by l_yy on 2017/1/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)  //指定测试用例的运行器 这里是指定了Junit4
@SpringApplicationConfiguration(classes = TestBootstrap.class) // 指定我们SpringBoot工程的Application启动类
@WebAppConfiguration
/*@TestPropertySource(properties = {"spring.cloud.config.uri=http://127.0.0.1.3:9010"})*/
//@ActiveProfiles("remote")
@ActiveProfiles("local")
//@ActiveProfiles("mock")
public abstract class BaseSpringMockTest {

    //protected T request;

    protected static String PROFILE;

    @Autowired
    private TestHelper testHelper;

    @Before
    public void setUp() throws Exception {
       PROFILE = testHelper.getCurrentProfile();

        //request = initRequest();
        if (Profile.MOCK.getValue().equals(PROFILE) || Profile.LOCAL.getValue().equals(PROFILE)) {
            MockitoAnnotations.initMocks(this);
            fullMock();
        }
        //addPrevTest(); /****/
    }

    /**
     *
     */
    protected void fullMock()  throws Exception  {
        fullMockInner();
    }

    /**
     * 业务相关的Mock
     */
    public abstract void fullMockInner() throws Exception   ;

}
