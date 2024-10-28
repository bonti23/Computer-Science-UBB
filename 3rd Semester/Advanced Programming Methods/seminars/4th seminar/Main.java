import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Main {

    Function<String, Integer> converterLambda= x->Integer.valueOf(x);
    // metoda generica
    public static <E> void printArie(List<E> l, Arie<E> f) {
        l.forEach(x -> System.out.println(f.compute(x)));
    }

    public static void main(String[] args) {

        Cerc cerc1 = new Cerc(2.0);
        Cerc cerc2 = new Cerc(3.0);

        List<Cerc> listaCercuri = Arrays.asList(cerc1, cerc2);

        Patrat patrat1 = new Patrat(1.0);
        Patrat patrat2 = new Patrat(2.0);

        List<Patrat> listaPatrate = Arrays.asList(patrat1, patrat2);

        // clasa interna
        Arie<Cerc> arieCerc = cerc -> Math.PI * cerc.getRaza() * cerc.getRaza();
        Arie<Patrat> ariePatrat = patrat -> patrat.getLatura() * patrat.getLatura();

//        System.out.println(arieCerc.compute(cerc1));
//        System.out.println(arieCerc.compute(cerc2));
//
//        System.out.println(ariePatrat.compute(patrat1));
//        System.out.println(ariePatrat.compute(patrat2));

        printArie(listaCercuri, arieCerc);
        printArie(listaPatrate, ariePatrat);

        List<String> stringList = Arrays.asList("ab", "abc", "aaabbbbcccc", "def", "xyz", "mnpq", "Al", "An", "nAnenuta", "Anel", "neluta");

        // afisare stringuri ce incep cu litera 'a'

        // sol1
        stringList.forEach(str -> {
            if (str.startsWith("a")) {
                System.out.println(str);
            }
        });

        // sol2
        stringList.stream()
                .filter(str -> str.startsWith("a"))
                .forEach(System.out::println);


        List<String> stringList2 = new ArrayList<>(stringList);
        stringList2.stream()
                .filter("Ana"::startsWith)
                .forEach(System.out::println);

        System.out.println();



        Predicate<String> estePrefix = "Ana"::startsWith;
        stringList2.removeIf(estePrefix);
        stringList2.forEach(System.out::println);

        List<String> stringList4 = Arrays.asList("mancare", "pui", "paine", "mar", "carne");
        stringList4.stream()
                .filter(str -> {
                    System.out.println(str);
                    return str.startsWith("b");
                })
                .map(str -> {
                    System.out.println(str);
                    return str.toUpperCase();
                })
                .forEach(System.out::println);

        Predicate<Character> eVocala = x-> {
            String vocale = "aeiouAEIOU";
            return vocale.contains(x.toString());
        };
        Function<String, Integer> converterLambda=x->Integer.valueOf(x);
        System.out.println(converterLambda.apply("14"));

        Function<String, Integer> converterMethodReference = Integer::valueOf;
        System.out.println(converterMethodReference.apply("123"));

        Function<String, String> converterPasareasca=x->{
            String rez="";
            for(int i=0; i<x.length(); i++){
                rez+=x.charAt(i);
                if(eVocala.test(x.charAt(i))){
                    rez+="p";
                    rez+=x.charAt(i);
                }
            }
            return rez;
        };
        System.out.println(converterPasareasca.apply("Mama merge la piata."));
        System.out.println(converterPasareasca.apply("Un vultur sta pe pisc cu un pix in plisc."));

        Supplier<Cerc> supplier = ()->new Cerc();
        Supplier<Cerc> supplier2 = Cerc::new;

        Cerc c1 = supplier.get();
        Cerc c2 = supplier.get();
        System.out.println(c1);
        System.out.println(c2);

        List<String> lista = List.of("asd", "bce", "asc", "bcr", "ccc");
        //stram = flux de date care permite operatii care se executa foarte eficient
        Stream<String> stream = lista.stream();
        stream.
                filter(x->{
                    System.out.println(x);
                    return x.startsWith("b");//daca ar fi inceput cu b ar fi trecut mai departe
                })
                .map(x->{
                    System.out.println(x);
                    return x.toUpperCase();
                })
                .forEach(System.out::println);
        //tema de adaugat o sortare




        List<Integer> intregi = List.of(1, 2, 3, 4, 5);
        Stream<Integer> stream2 = intregi.stream();
        Integer result = stream2.reduce(0,(a, b)->{
            //System.out.println("" + a + " " + b);
            return a+b;
        });
        System.out.println(result);

    }
}
