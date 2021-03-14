//package seu.talents.cloud.talent.controller;
//
//import org.junit.Assert;
//import org.junit.Rule;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.junit4.SpringRunner;
//import seu.talents.cloud.talent.service.CompanyService;
//
//@RunWith(SpringRunner.class)
//public class UnitTestController {
//    @Autowired
//    CompanyService companyService;
//
//    // 引入 ContiPerf 进行性能测试
//    @Rule
//    public ContiPerfRule contiPerfRule = new ContiPerfRule();
//
//    @Test
//    @PerfTest(invocations = 10000,threads = 100) //100个线程 执行10000次
//    public void test() {
//        String msg = "this is a test";
//        String result = companyService.getCompanyInfo(50240787934208);
//        //断言 是否和预期一致
//        Assert.assertEquals(msg,result);
//    }
//}
