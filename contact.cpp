/*ID: ohs.han1
TASK: contact
LANG: C++
 */

#include<iostream>
#include<cstdio>
#include<cstring>
#include<map>
#include<algorithm>
#include<fstream>
#include<vector>
using namespace std;

const int MAXN = 200100;

ifstream fin("contact.in");
ofstream fout("contact.out");

typedef string pattern;
vector<pattern> ar[MAXN];

bool sort_cmp(pattern a, pattern b) {
  if (a.length() == b.length()) {
    return a < b;
  } else {
    return a.length() < b.length();
  }
}

int main() {
  string S, tmp;
  map<string, int> mymap;
  int A, B, N;

  fin >> A >> B >> N;
  while(fin >> tmp) {
    S += tmp;
  }

  for(int i = 0; i < S.length(); i++) {
    for(int j = A; i + j <= S.length() && j <= B; j++) {
      mymap[S.substr(i, j)]++;
    }
  }

  for(map<string,int>::iterator it = mymap.begin(); it != mymap.end(); it++) {
    pattern p = it->first;
    int count = it->second;
    ar[count].push_back(p);
  }

  int freqs_printed = 0;
  for(int i = MAXN - 1; freqs_printed < N && i >= 0; --i) {
    vector<pattern> &v = ar[i];
    if (v.size() == 0) continue;

    fout << i << "\n";
    ++freqs_printed;
    sort(v.begin(), v.end(), sort_cmp);
    for (int j = 0; j < v.size(); ++j) {
      fout << v[j];
      if (j + 1 == v.size() || j % 6 == 5) {
        fout << "\n";
      } else {
        fout << " ";
      }
    }
  }

  return 0;
}
