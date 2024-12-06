;cd "C:\Users\alexa\OneDrive\Desktop\lispp"
;clisp
;(load "name.lisp")
(defun get_left (l)
    (cond
        ((null l) nil)
        ((listp (cadr l)) (cadr l))
        (t nil)
    )
)

(defun get_right (l)
    (get_left (cdr l))
)

(defun contine (l X)
    (cond 
        ((null l) nil)
        ((eq (car l) X) t)
        (t (or (contine (get_left l) X) (contine (get_right l) X)))
    )
)

(defun path(l X)
    (cond
        ((null l) nil)
        ((eq (car l) X) (list (car l)))
        ((contine (get_left l) X) (cons (car l) (path (get_left l) X)))
        ((contine (get_right l) X) (cons (car l) (path (get_right l) X)))
    )
)

