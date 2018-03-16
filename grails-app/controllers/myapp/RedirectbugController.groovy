package myapp

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class RedirectbugController {

    RedirectbugService redirectbugService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond redirectbugService.list(params), model:[redirectbugCount: redirectbugService.count()]
    }

    def show(Long id) {
        respond redirectbugService.get(id)
    }

    def create() {
        respond new Redirectbug(params)
    }

    def save(Redirectbug redirectbug) {

        if (redirectbug == null) {
            notFound()
            return
        }

        try {
            redirectbugService.save(redirectbug)
        } catch (ValidationException e) {
            respond redirectbug.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'redirectbug.label', default: 'Redirectbug'), redirectbug.id])
                redirect redirectbug
            }
            '*' { respond redirectbug, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond redirectbugService.get(id)
    }

    def update(Redirectbug redirectbug) {
        if (redirectbug == null) {
            notFound()
            return
        }

        try {
            redirectbugService.save(redirectbug)
        } catch (ValidationException e) {
            respond redirectbug.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'redirectbug.label', default: 'Redirectbug'), redirectbug.id])
                redirect redirectbug
            }
            '*'{ respond redirectbug, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        redirectbugService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'redirectbug.label', default: 'Redirectbug'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'redirectbug.label', default: 'Redirectbug'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
