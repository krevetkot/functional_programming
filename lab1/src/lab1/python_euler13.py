import argparse


def sum_digits_python(numbers):
    num_array = numbers[0].split(' ')
    total = sum(int(num) for num in num_array)
    return str(total)[:10]

def main(numbers):
    result = sum_digits_python(numbers)
    print(f"{result}")
    return result

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument("numbers", nargs='+', help="Список чисел через пробел")
    args = parser.parse_args()
    main(args.numbers)