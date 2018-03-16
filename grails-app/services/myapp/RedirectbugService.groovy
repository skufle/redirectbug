package myapp

import grails.gorm.services.Service

@Service(Redirectbug)
interface RedirectbugService {

    Redirectbug get(Serializable id)

    List<Redirectbug> list(Map args)

    Long count()

    void delete(Serializable id)

    Redirectbug save(Redirectbug redirectbug)

}