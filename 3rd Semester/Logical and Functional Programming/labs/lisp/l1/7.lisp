;cd "C:\Users\alexa\OneDrive\Desktop\lispp"
;clisp
;(load "name.lisp")
;a)
(defun liniara(listt)
	(cond
		((null listt) t)
		((listp(car listt))nil);false
		(t (liniara(cdr listt)));altfel
	)
)
	
;b)
(defun substituire(listt e1 e2 gasit)
	(cond
		((null listt) ());lista vida
		((and(eq(car listt)e1)(eq gasit 0)); inlocuim
			(cons e2(substituire(cdr listt) e1 e2 1))) ;cons = concatenare
		(t (cons (car listt)(substituire(cdr listt) e1 e2 gasit)))
	)
)

(defun substituireMain(listt e1 e2)
	(substituire listt e1 e2 0)
)

;c)
(defun ultimul(listt)
	(ultimul_aux listt ())
)

(defun ultimul_aux(listt c)
	(cond
		((null listt) c)
		(t (ultimul_aux(cdr listt)(car listt)))
	)
)

(defun substLista (listt)
	(cond
		((null listt) ())
		((atom (car listt))   (cons(car listt)(substLista(cdr listt))))
		(t (cons (ultimul (car listt))   (substLista(cdr listt))))
	)
)

(defun main(listt)
	(cond
		((liniara(substLista listt)) (substLista listt))
		(t (main (substLista listt)))
	)
)

;d)
(defun interclasare(a b)
	(cond
		((null a) b)
		((null b) a)
		((< (car a)(car b))(cons(car a)(interclasare(cdr a) b)))
		((< (car b)(car a))(cons(car b)(interclasare a (cdr b))))
		(t (cons(car a)(interclasare(cdr a)(cdr b))))
	)
)
