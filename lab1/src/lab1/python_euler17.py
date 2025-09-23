def number_to_words(n):
    units = ["", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine",
             "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen",
             "seventeen", "eighteen", "nineteen"]
    tens = ["", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"]
    
    if n == 1000:
        return "one thousand"
    elif n >= 100:
        hundreds = units[n // 100] + " hundred"
        remainder = n % 100
        if remainder == 0:
            return hundreds
        else:
            return hundreds + " and " + number_to_words(remainder)
    elif n >= 20:
        return tens[n // 10] + (" " + units[n % 10] if n % 10 != 0 else "")
    else:
        return units[n]

def count_letters_python(start, end):
    total_letters = 0
    for n in range(start, end + 1):
        words = number_to_words(n)
        clean_words = words.replace(" ", "")
        total_letters += len(clean_words)
    return total_letters

def main():
    result = count_letters_python(1, 1000)
    print(f"{result}")
    return result

if __name__ == "__main__":
    main()