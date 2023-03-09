CC = gcc
CFLAGS = -lncurses


%.o: %.c $(DEPS)
	$(CC) -c -o $@ $< $(CFLAGS)

treeview : treeview.o
	$(CC) -o treeview treeview.o $(CFLAGS)

info : info.o
	$(CC) -o info info.o $(CFLAGS)

clean: 
	rm *.o treeview info
