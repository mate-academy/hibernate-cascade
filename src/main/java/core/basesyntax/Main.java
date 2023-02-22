package core.basesyntax;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.dao.impl.SmileDaoImpl;
import core.basesyntax.model.Smile;
import java.util.List;

public class Main {
    private static SmileDao smileDao = new SmileDaoImpl(HibernateUtil.getSessionFactory());

    public static void main(String[] args) {

        Smile smile = new Smile();
        smile.setValue(":)");

        Smile smileFromDB = smileDao.create(smile);
        System.out.println(smileFromDB);
        List<Smile> allSmilesFromDb = smileDao.getAll();
        System.out.println(allSmilesFromDb);
    }
}
