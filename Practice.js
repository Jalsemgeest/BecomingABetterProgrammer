console.log();
console.log();
console.log();
console.log('insersection of love');

const rect1 = {
  leftX: 1,
  bottomY: 1,

  width: 10,
  height: 4,
};

const rect2 = {
  leftX: 3,
  bottomY: 1,

  width: 10,
  height: 4,
};

// Area should be same format

function findRectOverlap(point1, length1, point2, length2) {
  const firstLine = point1 < point2 ? { start: point1, end: point1 + length1 } : { start: point2, end: point2 + length2 };
  const secondLine = point1 >= point2 ? { start: point1, end: point1 + length1 } : { start: point2, end: point2 + length2 };

  if (secondLine.start < firstLine.end) {
    return {
      start: secondLine.start,
      end: secondLine.end < firstLine.end ? secondLine.end - secondLine.start : firstLine.end - secondLine.start,
    };
  }

  return null;
}

function findOverlap2(rectangle1, rectangle2) {
  const xOverlap = findRectOverlap(rectangle1.leftX, rectangle1.width, rectangle2.leftX, rectangle2.width);
  const yOverlap = findRectOverlap(rectangle1.bottomY, rectangle1.height, rectangle2.bottomY, rectangle2.height);
  if (xOverlap == null || yOverlap == null) {
    return 0;
  }
  console.log(`Total Area: ${xOverlap.end * yOverlap.end}`);
  return {
    leftX: xOverlap.start,
    bottomY: yOverlap.start,

    width: xOverlap.end,
    height: yOverlap.end,
  };
}

// console.log(findOverlap2(rect1, rect2));

const largerText = 'Given a list of the average stock price each day, pick a buying day and selling day to maximize profit. (They won’t ask this, but it’s an interesting question to think through. Google the answer - there’s a lot of optimizations you can make that you probably won’t think of. It gets complicated pretty fast though).';
const sentence = 'Determine if all the letters in a sentence can be found in a larger text';

// Determine if all the letters in a sentence can be found in a larger text
// Hashmap sentence with counts of each letter.

class SpecialHash {
  constructor(str) {
    this.values = [];
    this.count = 0;
    for (var i = 0; i < str.length; i++) {
      if (str[i] !== ' ') {
        this.hashPlus(str.charCodeAt(i));
      }
    }
  }

  hashPlus(charVal) {
    if (!this.values[charVal]) {
      this.values[charVal] = 1;
    } else {
      this.values[charVal]++;
    }
    this.count++;
  }

  removeChar(charVal) {
    if (this.values[charVal] && this.values[charVal] > 0) {
      this.values[charVal]--;
      this.count--;
    }
  }

  getCount() {
    return this.count;
  }
}

function hasIt(sent, text) {
  const map = new SpecialHash(sent);
  let pointer = 0;

  while (pointer < text.length) {
    const char = text.charCodeAt(pointer);
    map.removeChar(char);
    if (map.getCount() <= 0) {
      return true;
    }
    pointer += 1;
  }
  return false;
}

// console.log(hasIt(sentence, largerText));



// Given a stream of numbers (ie, you’re getting input one at a time), find the 5 largest

let test = [1, 5, 100, 6, 3, 9, 15, 24, 1200, 69]; // 1200, 100, 69, 24, 15

// Max heap



























console.log();
console.log();
console.log();
