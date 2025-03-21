(defun nivel(lista x niv)
	(cond
		((and(atom lista)(eq x lista))niv)
		((listp lista)(or(some #'(lambda (y)
									(nivel y x (+ 1 niv))
								)lista
						)
						nil)
				)
		(t nil)
	)
)
(defun main(lista x)
	(nivel lista x 0)
)
