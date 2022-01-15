name = input("Type your name: ")
print("Welcome,", name+ ", to this adventure!")

answer = input("You have awaken at last. There are two door, one to your left and one to your left. Which door are you taking? ").lower()

if answer == "left":
    answer = input("Behind the door there's a chest filled with bananas. Do you want to eat one?(yes/no) ").lower()
    if answer == "yes":
        print("You eat the banana. It is filled with potassium, and you die from overdose.")
    elif answer == "no":
        print("The spirit of bananas did not like that. You die of the Banana Curse.")
    else:
        print("Not a valid option. You lose.")
elif answer =="right":
    answer = input("Behind the door is Narnia. You see a lion, do you approach it or hide behind a tree? (approach/hide) ").lower()
    if answer == "approach":
        answer = input("You approach the lion. It invites you for a tea. Accept or decline? ").lower()
        if answer == "accept":
            print("You have a wonderful time with the lion. And you have a sleepover with all of the lion's friends.")
        elif answer == "decline":
            print("The lion runs off and your dream ends.")
        else:
            print("Not a valid option. You lose.")
    elif answer == "hide":
        print("Wise choice, yet it is not the best one. The lion does not move and you freeze to death waiting for it to move.")
    else:
        print("Not a valid option. You lose.")
else:
    print("Not a valid option. You lose.")
print("Thank you for trying,", name +".")