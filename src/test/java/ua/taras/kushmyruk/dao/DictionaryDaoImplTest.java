package ua.taras.kushmyruk.dao;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMConfiguration;
import ua.taras.kushmyruk.domain.CountyArea;
import ua.taras.kushmyruk.domain.PassporOffice;
import ua.taras.kushmyruk.domain.RegisterOffice;
import ua.taras.kushmyruk.domain.Street;
import ua.taras.kushmyruk.exceptions.DaoExceptions;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;


public class DictionaryDaoImplTest {
    private static final Logger logger = LoggerFactory.getLogger(DictionaryDaoImplTest.class);
    @BeforeClass
    public static void startup() throws URISyntaxException, IOException {
        DOMConfigurator.configure("src/test/resources/log4j.xml");
       DbInit.startup();
    }


    @Before
    public void startTest(){
        System.out.println("start");
    }
    @After
    public void afterTest(){
        System.out.println("after");
    }
    @Test
    public void testStreet() throws DaoExceptions {
        LocalDateTime dateTime = LocalDateTime.now();
        LocalDateTime dateTime2 = LocalDateTime.now();
        logger.trace("TEST {} {}" , dateTime, dateTime2);
        List<Street> d = new DictionaryDaoImpl().findStreets("про");
        Assert.assertEquals(2, d.size());
    }
    @Test
//    @Ignore
    public void testPassport() throws DaoExceptions {
        List<PassporOffice> passporOffices = new DictionaryDaoImpl().findPassportOfficie("010020000000");
        Assert.assertTrue(passporOffices.size() == 2);

    }

    @Test
    public void testRegister() throws DaoExceptions {
        List<RegisterOffice> ro = new DictionaryDaoImpl().findRegisterOffice("010020000000");
        Assert.assertEquals(1, ro.size());

    }
    @Test
    public void testAreas()throws DaoExceptions{
        List<CountyArea> ca1 = new DictionaryDaoImpl().findAreas("");
        List<CountyArea> ca2 = new DictionaryDaoImpl().findAreas("020000000000");
        List<CountyArea> ca3 = new DictionaryDaoImpl().findAreas("020010000000");
        List<CountyArea> ca4 = new DictionaryDaoImpl().findAreas("020010010000");

        Assert.assertEquals(2, ca1.size());
        Assert.assertEquals(2, ca2.size());
        Assert.assertEquals(2, ca3.size());
        Assert.assertEquals(2, ca4.size());

    }

}
