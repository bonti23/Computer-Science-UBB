package ro.mpp2024;

import java.io.Serializable;

public class Response implements Serializable {
    private ResponseType type;
    private Object data;

    private Response(){};


    public ResponseType type(){
        return type;
    }

    public Object data(){
        return data;
    }

    private void type(ResponseType type){
        this.type=type;
    }

    private void data(Object data){
        this.data=data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "type='" + type + '\'' +
                ", data='" + data + '\'' +
                '}';
    }

    // Adăugăm metoda fromString
    public static Response fromString(String str) {
        Response response = new Response();

        // Presupunem că șirul de caractere este de forma: "type=data"
        String[] parts = str.split(",", 2);  // presupunem că datele sunt separate prin virgulă
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid response string format");
        }

        String typePart = parts[0].split("=")[1].trim();  // extragem tipul
        String dataPart = parts[1].split("=")[1].trim();  // extragem datele

        // Analizăm tipul
        try {
            ResponseType type = ResponseType.valueOf(typePart);  // convertim string-ul la tipul enumerat
            response.type(type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid response type: " + typePart);
        }

        // Convertim datele într-un obiect
        // Presupunem că datele sunt un șir de caractere sau orice alt tip. Dacă sunt complexe, va trebui să adaugi logica de deserializare pentru ele.
        response.data(dataPart);  // în acest exemplu simplu, considerăm că data este doar un șir

        return response;
    }


    public static class Builder{
        private Response response=new Response();

        public Builder type(ResponseType type) {
            response.type(type);
            return this;
        }

        public Builder data(Object data) {
            response.data(data);
            return this;
        }

        public Response build() {
            return response;
        }
    }

}
