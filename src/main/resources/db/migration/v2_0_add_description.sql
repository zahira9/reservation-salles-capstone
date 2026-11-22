-- Migration v2.0 : ajoute une description aux salles
ALTER TABLE salles ADD COLUMN description VARCHAR(255);

-- Migration v2.0 : index unique sur l'adresse email
CREATE UNIQUE INDEX IF NOT EXISTS idx_utilisateur_email ON utilisateurs(email);
