# HOL-Light

## Setup

### Opam & OCaml

```bash
sudo apt install opam
opam switch create hol-light 4.14.1
eval $(opam env)
opam install utop core core_bench camlp5.8.00.05 num ocaml-lsp-server ocamlformat
```
I couldn't make HOL-Light work with the latest version of camlp5.

### OCaml Platform plugin for VSCode
Follow the instructions [here](https://marketplace.visualstudio.com/items?itemName=ocamllabs.ocaml-platform)

### Build and run HOL-Light

```bash
git clone https://github.com/jrh13/hol-light.git
cd hol-light
make
cd ..
code .
```

Use `hol-light-tutorial-script.ml` and send each line to REPL by SHIFT-ENTER. For some reason, utop doesn't play well with HOL-Light. In VSCode, under `File > Preferences > Settings` one can set if utop should be used for REPL or not.
