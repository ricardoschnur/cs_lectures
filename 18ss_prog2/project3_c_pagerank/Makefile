MKDIR      ?= mkdir -p
CPP        ?= cpp
CC         ?= cc

CFLAGS    += -std=c11 -Wall -pedantic
CPPFLAGS  += -MMD -I.
LDFLAGS   += -lm -lpthread -ldl

ifeq ($(DEBUG),0)
  CFLAGS += -O3 -DNDEBUG
else
  CFLAGS += -ggdb -O0
endif

Q ?= @

EXECUTABLE = pagerank
OBJDIR = obj
SRCDIR = src

all: $(EXECUTABLE)

SOURCES   = $(wildcard $(SRCDIR)/*.c)
OBJFILES  = $(patsubst $(SRCDIR)/%.c,$(OBJDIR)/%.o,$(SOURCES))

# rebuild everything if the Makefile changes
$(OBJFILES): Makefile

# include dependency files:
-include $(OBJFILES:.o=.d)

.PHONY: all clean tests

clean:
	$(Q)rm -f $(EXECUTABLE)
	$(Q)rm -rf $(OBJDIR)

$(EXECUTABLE): $(OBJFILES)
	$(Q)echo Linking $@
	$(Q)$(CC) $(LDFLAGS) -o $@ $^

obj/.dir:
	$(Q)$(MKDIR) $(@D)
	$(Q)date >$@

obj/%.o: $(SRCDIR)/%.c | obj/.dir
	$(Q)echo Compiling $<
	$(Q)$(CC) $(CPPFLAGS) $(CFLAGS) -c -o $@ $<

tests: $(EXECUTABLE)
	tests/run-tests.py $(EXECUTABLE)
