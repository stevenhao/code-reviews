import java.util.*;
import java.lang.*;
import java.io.*;

// this seems more convenient than HashMap<>
class DefaultHashMap<K,V> extends HashMap<K,V> {
  protected V defaultValue;
  public DefaultHashMap(V defaultValue) {
    this.defaultValue = defaultValue;
  }
  @Override
  public V get(Object k) {
    return containsKey(k) ? super.get(k) : defaultValue;
  }
}
class Main {
    final static int MAXN = 100100;
    final static int BLOCK_SIZE = 300; // approximate is good enough. Math.sqrt is quite expensive

    // moving this class higher up, instead of below the template
    class Query {
        int l;
        int r;
        int ind;

        int block;

        public Query(int pl, int pr, int pi)
        {
            l = pl;
            r = pr;
            ind = pi;
            block = l / BLOCK_SIZE;
        }
    }

    int n, m;

    // move important variables to a more "global" scope
    // i guess in java they're called fields
    // this way, they are easily found (when reading the code)
    // they are also readily available from helper functions

    List<Query> queries = new ArrayList<>();
    Map<Integer, Integer> hsh = new DefaultHashMap<Integer, Integer>(0);
    Set<Integer> valid = new HashSet<>();
    int[] nums = new int[MAXN];
    int[] ans = new int[MAXN];

    // update valid by inserting or deleting num
    void check(int num) {
        if (hsh.get(num) == num) {
            valid.add(num);
        } else {
            valid.remove(num); // ensure that num not in valid
        }
    }

    // remove a guy from hsh
    void remove(int num) {
        hsh.put(num, hsh.get(num) - 1);
        check(num);
    }

    // insert a guy to hsh,
    void insert(int num) {
        hsh.put(num, hsh.get(num) + 1);
        check(num);
    }

    void run() throws Exception {
        n = nextInt();
        m = nextInt();
        for(int i = 0; i < n; i++) {
            nums[i] = nextInt();
        }

        for(int i = 0; i < m; i++) {
            int l = nextInt(), r = nextInt();
            l--; r--; // use 0-indexing
            queries.add(new Query(l, r, i));
        }
        Collections.sort(queries, new CC()); // sort by sqrt-bucket (Mo's alg)

        int curL = 0, curR = 0; // put "paired" variables on the same line
        for (Query q: queries) {
            int l = q.l, r = q.r+1;
            // we set r = q.r+1 so that we are comparing half-open intervals [curL, curR) to half-open intervals [l,r).
            // the comparisons become more sensible this way (and look more symmetric), so we are less likely to bug here

            while(curL < l) {
                remove(nums[curL]);
                curL++;
            }

            while(curL > l) {
                insert(nums[curL-1]);
                curL--;
            }
            while(curR < r) {
                insert(nums[curR]);
                curR++;
            }
            while(curR > r)
            {
                remove(nums[curR-1]);
                curR--;
            }
            ans[q.ind] = valid.size();
        }

        for(int i = 0; i < m; i++) {
            out.println(ans[i]);
        }
        in.close();
        out.close();
    }

    // moving this code above template stuff
    static class CC implements Comparator<Query>
    {
        public int compare(Query c1, Query c2)
        {
            // slightly easier to read this way
            // Java's compare is traditionally implemented using the - operator
            if (c1.block == c2.block) return c1.r - c2.r;
            else return c1.block - c2.block;
        }
    }

    public static void main(String args[]) throws Exception {
        new Main().run(); // read from stdin/stdout
//              new Main("prog.in", "prog.out").run(); // or use file i/o
    }

    BufferedReader in;
    PrintWriter out;
    StringTokenizer st;

    Main() throws Exception {
        in = new BufferedReader(new InputStreamReader(System.in));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));;
    }

    Main(String is, String os) throws Exception {
        in = new BufferedReader(new FileReader(new File(is)));
        out = new PrintWriter(new File(os));
    }

    int nextInt() throws Exception {
        return Integer.parseInt(next());
    }

    long nextLong() throws Exception {
        return Long.parseLong(next());
    }

    String next() throws Exception {
        while (st == null || !st.hasMoreTokens()) {
            st = new StringTokenizer(in.readLine());
        }
        return st.nextToken();
    }


}
