import React, { useContext, useEffect, useState } from 'react';
import { RouteComponentProps } from 'react-router';
import BookComponent from './BookComponent';
import { getLogger } from '../core';
import { BookContext } from './BookProvider';
import {
  IonContent, IonHeader, IonList, IonLoading, IonPage, IonTitle, IonToolbar, IonToast,
  IonFab, IonFabButton, IonIcon, IonButton, IonButtons, IonInfiniteScroll, IonInfiniteScrollContent,
  IonSearchbar, IonSelect, IonSelectOption
} from '@ionic/react';
import { add } from 'ionicons/icons';
import { AuthContext } from '../auth';
import { NetworkState } from '../pages/NetworkState';
import { BookProps } from './BookProps';
import styles from "./styles.module.css";

const log = getLogger('BooksList');
const filterValues = ["Sold", "Not Sold"];
const itemsPerPage = 5;

export const BooksList: React.FC<RouteComponentProps> = ({ history }) => {
  const { books, fetching, fetchingError, successMessage, closeShowSuccess } = useContext(BookContext);
  const { logout } = useContext(AuthContext);
  const [shownBooks, setShownBooks] = useState<BookProps[]>([]);
  const [index, setIndex] = useState(0);
  const [more, setMore] = useState(true);
  const [filter, setFilter] = useState<string | undefined>();
  const [search, setSearch] = useState('');

  useEffect(() => { setShownBooks([]); setIndex(0); fetchMore(); }, [books]);
  useEffect(() => { if (filter && books) filterBooks(); }, [filter]);
  useEffect(() => { if (search && books) searchBooks(); }, [search]);

  function handleLogout() {
    logout?.();
    history.push('/login');
  }

  function filterBooks() {
    setShownBooks(
      books?.filter(b => filter === "Sold" ? b.sold : !b.sold) || []
    );
  }

  function searchBooks() {
    setShownBooks(
      books?.filter(b => b.title.toLowerCase().includes(search.toLowerCase())) || []
    );
  }

  function fetchMore() {
    if (!books) return;
    const newIndex = Math.min(index + itemsPerPage, books.length);
    setShownBooks(books.slice(0, newIndex));
    setIndex(newIndex);
    setMore(newIndex < books.length);
  }

  return (
    <IonPage>
      <IonHeader>
        <IonToolbar>
          <IonTitle>Books</IonTitle>
          <IonButtons slot="end"><IonButton onClick={handleLogout}>Logout</IonButton></IonButtons>
        </IonToolbar>
      </IonHeader>
      <IonContent fullscreen>
        <NetworkState />
        <IonSearchbar value={search} onIonInput={e => setSearch(e.detail.value!)} debounce={200} placeholder="Search..." />
        <IonSelect value={filter} placeholder="Filter" onIonChange={e => setFilter(e.detail.value)}>
          {filterValues.map(v => <IonSelectOption key={v} value={v}>{v}</IonSelectOption>)}
        </IonSelect>

        <IonLoading isOpen={fetching} message="Fetching books..." />
        {shownBooks.length > 0 && (
          <IonList>
            {shownBooks.map(book =>
              <BookComponent key={book._id} {...book} onEdit={id => history.push(`/book/${id}`)} />
            )}
          </IonList>
        )}
        <IonInfiniteScroll threshold="100px" disabled={!more} onIonInfinite={e => { fetchMore(); (e.target as any).complete(); }}>
          <IonInfiniteScrollContent loadingText="Loading more books..." />
        </IonInfiniteScroll>

        {fetchingError && <div>{fetchingError.message}</div>}
        <IonFab vertical="bottom" horizontal="end" slot="fixed">
          <IonFabButton onClick={() => history.push('/book')}>
            <IonIcon icon={add} />
          </IonFabButton>
        </IonFab>
        <IonToast
          isOpen={!!successMessage}
          message={successMessage}
          duration={5000}
          position="bottom"
          onDidDismiss={closeShowSuccess}
        />
      </IonContent>
    </IonPage>
  );
};
