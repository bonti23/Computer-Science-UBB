import React, { memo } from "react";
import { IonItem, IonLabel, IonNote, IonCheckbox, IonGrid, IonRow, IonCol } from "@ionic/react";
import { BookProps } from "./BookProps";
import styles from "./styles.module.css";

interface BookPropsExtended extends BookProps {
  onEdit: (_id?: string) => void;
}

const BookComponent: React.FC<BookPropsExtended> = ({ _id, title, launchDate, price, sold, webViewPath, onEdit }) => (
  <IonItem style={{ borderBottom: "1px solid #eee", padding: "8px" }} onClick={() => onEdit(_id)}>
    <IonGrid>
      <IonRow>
        <IonCol size="3">
          {webViewPath && <img src={webViewPath} alt={title} width="100%" height="150px" style={{ borderRadius: "10px" }} />}
        </IonCol>
        <IonCol size="6">
          <IonLabel className={styles.bookLabel}>
            <IonItem><IonLabel>Title: {title}</IonLabel></IonItem>
            <IonItem><IonLabel>Price: {price}</IonLabel></IonItem>
          </IonLabel>
          <IonNote>
            Launch: {launchDate ? new Date(launchDate).toLocaleDateString() : "Unknown"}
          </IonNote>
        </IonCol>
        <IonCol size="3" className="ion-text-center">
          <IonCheckbox checked={sold} disabled />
          <IonLabel>Sold</IonLabel>
        </IonCol>
      </IonRow>
    </IonGrid>
  </IonItem>
);

export default memo(BookComponent);
