# Let's build a compiler

An attempt to follow along with Jack Crenshaw's [Let's build a compiler](https://compilers.iecc.com/crenshaw/), with a few differences:

 * The original is written in Turbo Pascal, but I know Scala better, so I'm porting as I go along.
   This leads to some fairly non-idiomatic Scala code, but I'm trying to stick fairly closely to
   the original.
 * The original generates assembly language for the [68000](https://en.wikipedia.org/wiki/Motorola_68000),
   but my machine has an x64 processor, so I'm changing the output to be suitable for feeding into
   [nasm](https://www.nasm.us/), with the help of [0xAX](https://twitter.com/0xAX)'s [Say hello to x86_64 Assembly](https://github.com/0xAX/asm).

## Running

If you have `sbt` installed, you should be able to run this thing like this:

    $ echo '3-5*2+6/2' | sbt --error run > expression.asm

If you have `nasm` installed, you can then generate an executable binary like this:

    $ ./assemble.sh expression.asm

And then you can run the binary!

    $ ./target/expression
