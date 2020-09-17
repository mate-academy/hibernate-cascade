package core.basesyntax.dao.impl;

import java.util.List;

import core.basesyntax.AbstractTest;
import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import org.junit.Assert;
import org.junit.Test;

public class SmileDaoImplTest extends AbstractTest {

    @Test
    public void shouldListSmiles() {
        SmileDao smileDao = new SmileDaoImpl(getSessionFactory());

        Smile funny = new Smile("funny");
        Smile sad = new Smile("sad");
        Smile relieved = new Smile("relieved");
        Smile disappointed = new Smile("disappointed");

        smileDao.create(funny);
        smileDao.create(sad);
        smileDao.create(relieved);
        smileDao.create(disappointed);

        List<Smile> smiles = smileDao.getAll();
        Assert.assertNotNull(smiles);
        Assert.assertFalse(smiles.isEmpty());
        Assert.assertEquals(4, smiles.size());
        System.out.println(smiles);
    }

    @Override
    protected Class<?>[] entities() {
        return new Class<?>[]{
                Smile.class
        };
    }
}
