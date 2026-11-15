import React, { useCallback, useContext, useState } from 'react';
import {
  IonButton, IonButtons, IonContent, IonHeader, IonInput,
  IonLabel, IonDatetime, IonCheckbox, IonPage, IonTitle, IonToolbar,
  IonBackButton, IonLoading
} from '@ionic/react';
import { getLogger } from '../core';
import { RouteComponentProps } from 'react-router';
import { BookContext } from './BookProvider';
import styles from './styles.module.css';

const log = getLogger('BookAdd');

interface BookAddProps extends RouteComponentProps<{ id?: string }> {}

export const BookAdd: React.FC<BookAddProps> = ({ history }) => {
  const { updating, updateError, addBook } = useContext(BookContext);
  const [title, setTitle] = useState('');
  const [launchDate, setLaunchDate] = useState(new Date());
  const [price, setPrice] = useState(0);
  const [sold, setSold] = useState(false);

  const handleAdd = useCallback(() => {
    const newBook = { title, launchDate, sold, price };
    addBook?.(newBook).then(() => history.goBack());
  }, [addBook, title, launchDate, sold, price, history]);

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonButtons slot="start"><IonBackButton /></IonButtons>
          <IonTitle>Add Book</IonTitle>
          <IonButtons slot="end"><IonButton onClick={handleAdd}>Add</IonButton></IonButtons>
        </IonToolbar>
      </IonHeader>
      <IonContent className={styles.editContent}>
        <IonLabel>Title</IonLabel>
        <IonInput value={title} placeholder="Enter title" onIonChange={e => setTitle(e.detail.value || '')} />

        <IonLabel>Launch Date</IonLabel>
        <IonDatetime presentation="date" value={launchDate.toISOString()}
            onIonChange={(e) => {
                const value = e.detail.value as string | null;
                if (value) {
                    setLaunchDate(new Date(value));
                }
            }}/>
        <IonLabel>Sold</IonLabel>
        <IonCheckbox checked={sold} onIonChange={e => setSold(e.detail.checked)} />

        <IonLabel>Price</IonLabel>
        <IonInput
          type="number"
          value={price}
          placeholder="Enter price"
          onIonChange={e => setPrice(Number(e.detail.value || 0))}
        />

        <IonLoading isOpen={updating} />
        {updateError && <div className={styles.errorMessage}>{updateError.message}</div>}
      </IonContent>
    </IonPage>
  );
};
