# monad workshop

This repo is meant as a support for the "_Beating the monad curse_" workshop at
Heart of Clojure 2024. It likely won't make sense on its own, without the extra
context of that event. You're still welcome to look around, though.

## Resources

I have written about monads in the past. If you don't have the extra context of
the conference, or you want to dig deeper, you can read my [entire series on
monads][monads] and/or my [Clojure-specific monad introduction][clojure], which
should be understandable on its own.

[monads]: https://cuddly-octo-palm-tree.com/tags/monad-tutorial/
[clojure]: https://cuddly-octo-palm-tree.com/posts/2021-10-03-monads-clojure/

## Introduction

Monads, as a programming technique, have been invented by the Haskell
community. They are widely used in Haskell, and somewhat used in Scala and
OCaml, but pretty much absent from most other programming ecosystems.

For the purposes of this workshop/repo, we'll make the oversimplification that
they are only used in Haskell, and try to reason out why that is.

Rough outline of the plan:

### Brief history of Haskell

Haskell started as a pure, lazy language with no IO. In the beginning it was
just an interactive shell, then they got a "string to string" API, but that
obviously wasn't enough.

### Lazy IO problem

Without IO, a program is useless. Constraints:

- We want useful programs!
- We want to write only pure code. (Caveat.)
- We want a lazy language, in which order of operation is essentially undefined.
- We want the order of side-effects to be predictable, and useful.

Solution: monad. Well, that doesn't help much at this point, so let's go for a
more verbose version.

### Properties IO needs to have

In which we pragmatically arrive at the monad laws, eventually. But just for
IO.

### Generalizing

For reference:

```haskell
return a >>= h  === h a
m >>= return    === m
(m >>= g) >>= h === m >>= (\x -> g x >>= h)
```

assuming:

```haskell
class Monad m where
    (>>=) :: m a -> (a -> m b) -> m b
    return :: a -> m a
```

### Clarifying (by building)

Make all tests pass.

### Taking a step back: what did we do?

Where we discuss what monads actually do, and why the monad abstraction can be
useful.

### Why are monads not used more often?

They solve a somewhat rare problem most languages have alternatives for.
Haskell is special because it includes return types in sugnatures, it is lazy,
and it pretends to be pure.

### Why _are_ they used so often in Haskell then?

If you have to have IO, then it makes sense to build tooling around Monad, and
then it makes sense to express all sorts of other things in terms of it.

### Composing monads

Monads do not compose. (At least not without a lot of pain.) Generally
speaking, you're better off implementing a new monad that combines the features
of the ones you want to combine.

## License

Copyright © 2024 Gary Verhaegen

This program and the accompanying materials are made available under the
terms of the Eclipse Public License 2.0 which is available at
http://www.eclipse.org/legal/epl-2.0.
