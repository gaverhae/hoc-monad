{-# LANGUAGE GADTs #-}

module Main (main) where

import Control.Monad

instance Functor M where fmap = liftM
instance Applicative M where pure = return; (<*>) = ap
instance Monad M where return = Return; (>>=) = Bind

data M a where
  Bind :: M a -> (a -> M b) -> M b
  Return :: a -> M a

example_do :: M Int
example_do = do
  a <- pure 5
  b <- pure 7
  return (a + b)

example_expanded :: M Int
example_expanded = Bind (Return 5) (\a -> Bind (Return 18) (\b -> Return (a + b)))

run :: M a -> a
run ma = case ma of
  Return a -> undefined
  Bind ma f -> undefined

-- We want this to print 12
main :: IO ()
main = putStrLn (show (run example_do))


































-- Maybe, usual presentation

-- Maybe, understandable presentation
--
instance Functor ShortCircuit where fmap = liftM
instance Applicative ShortCircuit where pure = return; (<*>) = ap
instance Monad ShortCircuit where return = SC_Return; (>>=) = SC_Bind

data ShortCircuit a where
  SC_Bind :: ShortCircuit a -> (a -> ShortCircuit b) -> ShortCircuit b
  SC_Return :: a -> ShortCircuit a
  SC_Stop :: ShortCircuit a

run_SC :: ShortCircuit a -> Maybe a
run_SC ma = case ma of
  SC_Return a -> Just a
  SC_Bind ma f -> case run_SC ma of
    Nothing -> Nothing
    Just a -> run_SC (f a)
  SC_Stop -> Nothing
