import random

toprange = input("Type an upper number: ")

if toprange.isdigit():
    toprange = int(toprange)
    if toprange <= 0:
        print("Number must be more larger than 0")
        quit()
else:
    print("Type a number")
    quit()

random_number= random.randint(0,toprange)
guestimates = 0

while True:
    guestimates += 1
    user_guess = input("Make a guess: ")
    if user_guess.isdigit():
        user_guess = int(user_guess)
    else:
        print("Type a number")
        continue

    if user_guess == random_number:
        print("You guessed correct.")
        break
    elif user_guess > random_number:
            print("Guess was above the number")
    else:
            print("Guess was below the number")
    
print("Attempts until success:", guestimates)