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
using namespace std;

const int MAXN = 400000;

ifstream fin("contact.in");
ofstream fout("contact.out");

typedef struct
{
  string pattern;
  int count;
}frequencies;
frequencies ar[MAXN];

bool sort1(frequencies a1, frequencies a2) {
  int x = a1.pattern.length();
  int y = a2.pattern.length();
  if(a1.count == a2.count) {
    if(x == y) {
      return a1.pattern < a2.pattern;
    }
    else
    {
      return x < y;
    }
  }
  return a1.count > a2.count;
}

int main() {
  string str1;
  string str2;
  map<string,int> mymap;
  int A, B, N;
  fin >> A >> B >> N;
  str1 = "";
  while(fin >> str2) {
    str1 += str2;
  }
  int length1 = str1.length();
  for(int i = 0; i < length1; i++) {
    for(int j = A; j <= B; j++)
    {
      if ((i+j) <= length1) {
        str2 = str1.substr(i,j);
        mymap[str2]++;
      }
      else {
        continue;
      }
    }
  }
  int w = 1;
  for(map<string,int>::iterator it = mymap.begin(); it != mymap.end(); it++) {
    ar[w].pattern = it->first;
    ar[w].count = it->second;
    w++;
  }
  sort(ar + 1, ar + w, sort1);
  int numCount = 1;
  int x = ar[1].count;
  int y = 1;
  fout << x << endl;
  fout << ar[1].pattern;
  for(int i = 2; i < w; i++) {
    if (ar[i].count== ar[i-1].count) {
      if (numCount == 0) {
        fout << ar[i].pattern;
      }
      else {
        fout << " " << ar[i].pattern;
      }
      numCount++;
      if(numCount == 6) {
        fout << "\n";
        numCount = 0;
      }
    }
    else {
      if (N == y) {
        break;
      }
      if(numCount != 0) {
        fout << "\n";
      }
      fout << ar[i].count << "\n";
      fout << ar[i].pattern;
      numCount = 1;
      y++;
    }
  }
  if(numCount != 0) {
    fout << "\n";
  }
  return 0;
}
