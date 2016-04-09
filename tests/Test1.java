public class Test1 {

    int a, b, f, c;

    Dummy dummy = new Dummy();

    Dummy hi = dummy;

    String e = "hi";

    int unassignedField = a;

    public class Dummy extends String {

        String a = "";

        int test() {
            foo(false);
        }
    }

    public int foo(boolean x) {
        int a;
        Dummy c = new Dummy();
        int h;
        String o = "";
        int b = 4;
        h = foo(true);
        b = h;
        int eija = b + 4 + 9;
        eija += b;
        eija = 4++;
        String j = null;
        Dummy i = null;
        int f, g;
    }
}
