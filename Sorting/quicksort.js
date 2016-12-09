let testArr = [1,3,5,2,4];

function quickSort(arr) {
  quickSortHelper(arr, 0, arr.length - 1);
}

function quickSortHelper(arr, low, high) {
  const index = partition(arr, low, high);
  if (low < index - 1) {
    quickSortHelper(arr, low, index - 1);
  }
  if (index < high) {
    quickSortHelper(arr, index, high);
  }
}

function partition(arr, left, right) {
  let middle = arr[Math.floor((left + right) / 2)];

  while (left <= right) {

    while (arr[left] < middle) left++;

    while (arr[right] > middle) right--;

    if (left <= right) {
      swap(arr, left, right);
      left++;
      right--;
    }
  }

  return left;
}

function swap(arr, left, right) {
  let oldLeft = arr[left];
  arr[left] = arr[right];
  arr[right] = oldLeft;
}

console.log(testArr);
quickSort(testArr);
console.log(testArr);