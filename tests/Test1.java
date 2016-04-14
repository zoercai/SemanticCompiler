public class Test1 {

    int a, b, c, d;

    Dummy dummy = new Dummy();

    Dummy hi = dummy;

    String e = "hi";

    int unassignedFieldGood = a;

    public class Dummy extends String {

        String b = forwardGood;

        int test() {
        }
    }

    public int foo(boolean x) {
        int a;
        String o;
        o = forwardGood;
        Dummy c = new Dummy();
        {
            int h;
            int y = 2;
        }
        int varNotGood = 99;
        int h;
        int b = 4;
        h = foo(true);
        b = h;
        int eija = b + 4 + 9;
        eija += b;
        eija = b++;
        boolean lll = false;
        boolean z = !lll;
        Dummy i = null;
        int f, g;
    }

    String forwardGood;
}
