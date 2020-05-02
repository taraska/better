package ru.ivannikov.better.model;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentityGenerator;

import java.io.Serializable;

public class AssignedIdentityGenerator extends IdentityGenerator {

    /**
     * Generate id or return current
     *
     * @param session current session
     * @param obj     object with getId() method
     * @return id
     */
    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        if (obj instanceof Identifiable) {
            Identifiable identifiable = (Identifiable) obj;
            Serializable id = identifiable.getId();
            if (id != null) {
                return id;
            }
        }
        return super.generate(session, obj);
    }
}
