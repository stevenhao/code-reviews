import java.util.*;
import java.lang.*;
import java.io.*;

public class Main {

    static int n;
    static int m;

    void run() throws Exception {

//// sample program begins here
//        int a, b;
//        a = nextInt();
//        b = nextInt();
//        out.println(a + b);

        n = nextInt();
        m = nextInt();
        int[] nums = new int[n];
        for(int i = 0; i < n; i++)
        {
            nums[i] = nextInt();
        }

        CC c = new CC();
        TreeSet<Query> tr = new TreeSet<>(c);

        for(int i = 0; i < m; i++)
        {
            tr.add(new Query(nextInt()-1, nextInt()-1, i)); // reindex
        }

        int curL = 0;
        int curR = 0;

        HashMap<Integer, Integer> hsh = new HashMap<>();

        HashSet<Integer> valid = new HashSet<>();

        int[] ans = new int[m];

        while(tr.size() > 0)
        {
            Query q = tr.pollFirst();

            int l = q.l;
            int r = q.r;

            while(curL < l)
            {
                // need to remove curL
                if(!hsh.containsKey(nums[curL])) hsh.put(nums[curL], -1);
                else hsh.put(nums[curL], hsh.get(nums[curL]) - 1);

                int val = hsh.get(nums[curL]);
                if(val == nums[curL]) valid.add(nums[curL]);
                else if(val != nums[curL] && valid.contains(nums[curL])) valid.remove(nums[curL]);
                curL++;
            }
            while(curL > l)
            {
                if(!hsh.containsKey(nums[curL-1])) hsh.put(nums[curL-1], 1);
                else hsh.put(nums[curL-1], hsh.get(nums[curL-1])+1);
                curL--;

                int val = hsh.get(nums[curL]);

                if(val == nums[curL]) valid.add(nums[curL]);
                else if(val != nums[curL] && valid.contains(nums[curL])) valid.remove(nums[curL]);
            }
            while(curR <= r)
            {
                if(!hsh.containsKey(nums[curR])) hsh.put(nums[curR], 1);
                else hsh.put(nums[curR], hsh.get(nums[curR])+1);


                int val = hsh.get(nums[curR]);

                if(val == nums[curR]) valid.add(nums[curR]);
                else if(val != nums[curR] && valid.contains(nums[curR])) valid.remove(nums[curR]);


                curR++;
            }
            while(curR > r+1)
            {
                if(!hsh.containsKey(nums[curR-1])) hsh.put(nums[curR-1], -1);
                else hsh.put(nums[curR-1], hsh.get(nums[curR-1]) - 1);
                curR--;

                int val = hsh.get(nums[curR]);

                if(val == nums[curR]) valid.add(nums[curR]);
                else if(val != nums[curR] && valid.contains(nums[curR])) valid.remove(nums[curR]);
            }


//            int check = 0;
//
//            for(int key : hsh.keySet())
//            {
//                if(key == hsh.get(key))
//                {
//                    check++;
//                }
//            }

            ans[q.ind] = valid.size();

        }

        for(int i = 0; i < ans.length; i++)
        {
            out.println(ans[i]);
        }
        in.close();
        out.close();
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

    static class CC implements Comparator<Query>
    {
        public int compare(Query c1, Query c2)
        {
            if((int) (c1.l / Math.sqrt(n)) < (int) (c2.l / Math.sqrt(n))) return -1;
            else if((int) (c2.l / Math.sqrt(n)) < (int) (c1.l / Math.sqrt(n))) return 1;
            else if(c1.r < c2.r) return -1;
            else return 1;
        }
    }

}

class Query
{
    int l;
    int r;
    int ind;

    public Query(int pl, int pr, int pi)
    {
        l = pl;
        r = pr;
        ind = pi;
    }
}
