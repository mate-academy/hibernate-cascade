package core.basesyntax.dao.impl;

import core.basesyntax.dao.SmileDao;
import core.basesyntax.model.Smile;
import org.hibernate.SessionFactory;

public class SmileDaoImpl extends GenericDaoImpl<Smile> implements SmileDao {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory, Smile.class, "SELECT s FROM Smile s");
    }
}
