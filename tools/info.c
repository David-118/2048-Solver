#include <stdio.h>
#include <stdlib.h>

#define DEPTH 8

void printOverview(FILE* file);

int main(int argc, char *argv[]) {
    for (int i = 1; i < argc; i++) {
	FILE *f = fopen(argv[i], "r");
	printOverview(f);
	fclose(f);
    }
}

void printOverview(FILE* file) {
    int c = 0;
    int count = 0;
    int depth[DEPTH];

    for (int i = 0; i < DEPTH; i++) {
	depth[i] = 0;
    }

    while ((c=getc(file)) != EOF) {
        if (c==' ') {
	    count++;
	} else if (c=='\n') {
	    depth[count]++;
	    count = 0;
	} 
    }

    printf("%d", depth[0]);
    for (int i = 1; i < DEPTH; i++) {
	printf(",%d", depth[i]);
    }
}
