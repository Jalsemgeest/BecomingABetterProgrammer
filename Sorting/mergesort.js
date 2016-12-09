let arrTesting = [1, 3, 5, 2, 4];

function mergeSort(arr) {
  const helper = arr.map(a => 0);
  mergeSortHelper(arr, helper, 0, arr.length - 1);
}

function mergeSortHelper(arr, helper, low, high) {
  if (low < high) {
    const middle = Math.floor((low + high) / 2);
    mergeSortHelper(arr, helper, low, middle);
    mergeSortHelper(arr, helper, middle + 1, high);
    merge(arr, helper, low, middle, high);
  }
}

function merge(arr, helper, low, middle, high) {
  for (let i = low; i <= high; i++) {
    helper[i] = arr[i];
  }

  let left = low;
  let right = middle + 1;
  let current = low;

  while (left <= middle && right <= high) {
    if (helper[left] <= helper[right]) {
      arr[current] = helper[left];
      left++;
    } else {
      arr[current] = helper[right];
      right++;
    }
    current++;
  }

  const remaining = middle - left;
  for (let i = 0; i <= remaining; i++) {
    arr[current + i] = helper[left + i];
  }
}

console.log(arrTesting);
mergeSort(arrTesting);
console.log(arrTesting);
