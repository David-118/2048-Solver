// Please not this program requires ncurses.

#include <ncurses.h>
#include <stdlib.h>
#include <stdio.h>
#include <sys/ioctl.h>

#define SELECTED 1

int sindex = 0;


struct tree
{
  int vals[16];
  int show_c;
  int childCount;
  double h;
  double d;
  struct tree** children;
  char m;
};

struct tree** menu;
int rows_count;
int offset = 1;


void mkTree(struct tree* t);
void drawTree(struct tree* t, int* row, int col);
void selectOption(int row);
struct tree* readf(FILE *f);
void mkNode(struct tree* t);

int main(int argc, char *argv[])
{
  struct tree* root;
  struct winsize w;
  ioctl(0, TIOCGWINSZ, &w);
  rows_count = w.ws_row;
  FILE* f = fopen(argv[1], "r");
  root = readf(f);
  fclose(f);
  getch();
  initscr();
  start_color();
  init_pair(1, COLOR_BLACK, COLOR_WHITE);
  
  curs_set(0);
  noecho();
  
  while (1)
  {
    int row = 0;
    drawTree(root, &row, 3);
    refresh();
    selectOption(row);
  }
  endwin();

}

struct tree* readf(FILE* f)
{
  printf("%d", getenv("COLUMNS"));
  int count;
  int c;
  int l = 0;
  int m = 0;
  int id = 0;
  struct tree** depths = malloc(sizeof(struct tree*));
  struct tree* c_node = malloc(sizeof(struct tree));
  mkNode(c_node);
  while ((c = getc(f)) != EOF)
  {
    if (c=='#')
    {
      if (id < 16)
      {
        fscanf(f, "%d", (c_node->vals)+ id);
        id++;
      }
      else if (id == 16)
      {
        fscanf(f, "%lf", &(c_node->h));
        id++;
      }
      else if (id == 17)
      {
        fscanf(f, "%lf", &(c_node->d));
        id++;
      }

    } 
    else if (c == ' ')
    {
      l++;
    }
    else if (c == 'L' || c == 'M' || c =='C')
    {
      c_node -> m = c;
    }
    else if (c == '\n')
    {
      count++;
      if (m <= l) 
      {
        depths = realloc(depths, sizeof(struct tree*) * (l+1));
        m = l;
      }
      depths[l] = c_node;
      if (l > 0)
      {
        if (depths[l-1]->childCount==0)
        {
          depths[l-1]->children = malloc(sizeof(struct tree*));
          depths[l-1]->childCount = 1;
        }
        else
        {
          depths[l-1]->childCount++;
          depths[l-1]->children = realloc(depths[l-1]->children, sizeof(struct tree*) * depths[l-1]->childCount);
        }
        depths[l-1]->children[depths[l-1]->childCount - 1] = c_node;
      }
      id = 0;
      l = 0;
      c_node = malloc(sizeof(struct tree));
      mkNode(c_node);
    }
  }
  menu = malloc(sizeof(struct tree**)*count); 
  return depths[0];
}



void mkNode(struct tree* t)
{
  t->show_c = 0;
  t->childCount = 0;
  t->h = 0.0;
  t->d = 0.0;
}

void drawNode(struct tree* node, int row, int col)
{
  if ((0 <= (row + offset)) && ((row + offset) < rows_count))
  {
    move(row+offset, col);
    if (row==sindex) attron(COLOR_PAIR(1));
    printw("(%c)  ", node->m);
    printw("Values: ");
    for (int i = 0; i < 16; i++) printw("%d ", node->vals[i]);
    printw("  Heurisitc: %lf ", node->h);
    printw("  Derived Score: %lf", node->d);
    if (row==sindex) attroff(COLOR_PAIR(1));
  }
}

void drawTree(struct tree* root, int* row, int col)
{
  drawNode(root, *row, col);
  menu[*row] = root;

  (*row)++;
  if (root -> show_c)
  {
    for (int i = 0; i < root -> childCount; i++)
    {
      drawTree(root->children[i],row, col + 2);
    }
  }
}

void selectOption(int max)
{
  int c = getch();
  clear();
  switch (c)
  {
    case 65:
      if (sindex > 0) sindex--;
      break;
    case 66:
      if (sindex < max - 1) sindex++;
      break;
    case 10:
      menu[sindex]->show_c = !menu[sindex]->show_c;
      break;
    case 43:
      offset++;
      break;
    case 45:
      offset--;
      break;
  }
  mvprintw(sindex + offset, 0, "->");

}
