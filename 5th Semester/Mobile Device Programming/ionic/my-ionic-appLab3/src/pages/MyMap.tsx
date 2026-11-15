import React, { useRef, useEffect, useState } from "react";
import { GoogleMap } from "@capacitor/google-maps";
import { IonButton } from "@ionic/react";
import { mapsApiKey } from "./mapsApiKey";

interface MyMapProps {
  lat?: number;
  lng?: number;
  onCoordsChanged: (lat: number, lng: number) => void;
}

export const MyMap: React.FC<MyMapProps> = ({ lat, lng, onCoordsChanged }) => {
  const mapRef = useRef<HTMLDivElement>(null);
  const map = useRef<GoogleMap | null>(null);
  const markerId = useRef<string | null>(null);

  const [latitude, setLatitude] = useState<number>(lat ?? 46.77); // default Cluj :)
  const [longitude, setLongitude] = useState<number>(lng ?? 23.59);
  const [mapLoaded, setMapLoaded] = useState<boolean>(false);

  useEffect(() => {
    // actualizează markerul dacă s-au schimbat coordonatele din props
    if (mapLoaded && map.current && latitude && longitude) {
      updateMarker(latitude, longitude);
    }
  }, [lat, lng]);

  async function createMap() {
    if (!mapRef.current) return;

    // creează o singură hartă
    if (map.current) {
      console.log("Map already exists");
      return;
    }

    console.log("Creating map...");
    map.current = await GoogleMap.create({
      id: "my-map",
      element: mapRef.current,
      apiKey: mapsApiKey,
      config: {
        center: { lat: latitude, lng: longitude },
        zoom: 8,
      },
    });

    setMapLoaded(true);

    // adaugă marker inițial
    await updateMarker(latitude, longitude);

    // ascultă click-uri pe hartă
    await map.current.setOnMapClickListener(async (event) => {
      console.log("Clicked:", event.latitude, event.longitude);
      await updateMarker(event.latitude, event.longitude);
      setLatitude(event.latitude);
      setLongitude(event.longitude);
      onCoordsChanged(event.latitude, event.longitude);
    });
  }

  async function updateMarker(lat: number, lng: number) {
    if (!map.current) return;

    // elimină markerul anterior
    if (markerId.current) {
      await map.current.removeMarker(markerId.current);
    }

    // adaugă marker nou
    markerId.current = await map.current.addMarker({
      coordinate: { lat, lng },
    });
  }

  return (
    <div className="component-wrapper">
      <IonButton expand="block" onClick={createMap}>
        Create Map
      </IonButton>
      <div
        ref={mapRef}
        style={{
          width: "100%",
          height: "400px",
          marginTop: "8px",
          borderRadius: "10px",
          overflow: "hidden",
        }}
      ></div>
    </div>
  );
};
