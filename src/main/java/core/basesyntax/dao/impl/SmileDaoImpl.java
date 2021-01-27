package core.basesyntax.dao.impl;

import core.basesyntax.model.Smile;
import org.hibernate.SessionFactory;

public class SmileDaoImpl extends AbstractDao<Smile> {
    public SmileDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }
}
