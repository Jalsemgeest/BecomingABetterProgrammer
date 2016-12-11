// Basic Linked List
class Link {
  constructor({ data, next, previous }) {
    this.data = data;
    this.next = next;
    this.previous = previous;
  }

  insert(data) {
    if (!this.next) {
      this.next = new Link({ data, next: null, previous: this });
    } else {
      this.next.insert(data);
    }
  }

  getKey(data) {
    // Would have to specify how to determine if
    // an object is equal to another object.
    if (this.data === data) return this.data;
    if (!this.next) return null;
    return this.next.getKey(data);
  }
}

// Get random name
function randomName() {
  const adjectives = ['adamant', 'adroit', 'amatory', 'animistic', 'antic', 'arcadian', 'baleful', 'bellicose', 'bilious', 'boorish', 'calamitous', 'caustic', 'cerulean', 'comely', 'concomitant', 'contumacious', 'corpulent', 'crapulous', 'defamatory', 'didactic', 'dilatory', 'dowdy', 'efficacious', 'effulgent', 'egregious', 'endemic', 'equanimous', 'execrable', 'fastidious', 'feckless', 'fecund', 'friable', 'fulsome', 'garrulous', 'guileless', 'gustatory', 'heuristic', 'histrionic', 'hubristic', 'incendiary', 'insidious', 'insolent', 'intransigent', 'inveterate', 'invidious', 'irksome', 'jejune', 'jocular', 'judicious', 'lachrymose', 'limpid', 'loquacious', 'luminous', 'mannered', 'mendacious', 'meretricious', 'minatory', 'mordant', 'munificent', 'nefarious', 'noxious', 'obtuse', 'parsimonious', 'pendulous', 'pernicious', 'pervasive', 'petulant', 'platitudinous', 'precipitate', 'propitious', 'puckish', 'querulous', 'quiescent', 'rebarbative', 'recalcitant', 'redolent', 'rhadamanthine', 'risible', 'ruminative', 'sagacious', 'salubrious', 'sartorial', 'sclerotic', 'serpentine', 'spasmodic', 'strident', 'taciturn', 'tenacious', 'tremulous', 'trenchant', 'turbulent', 'turgid', 'ubiquitous', 'uxorious', 'verdant', 'voluble', 'voracious', 'wheedling', 'withering', 'zealous'];
  const nouns = ['ninja', 'chair', 'pancake', 'statue', 'unicorn', 'rainbows', 'laser', 'senor', 'bunny', 'captain', 'nibblets', 'cupcake', 'carrot', 'gnomes', 'glitter', 'potato', 'salad', 'toejam', 'curtains', 'beets', 'toilet', 'exorcism', 'stick figures', 'mermaid eggs', 'sea barnacles', 'dragons', 'jellybeans', 'snakes', 'dolls', 'bushes', 'cookies', 'apples', 'ice cream', 'ukulele', 'kazoo', 'banjo', 'opera singer', 'circus', 'trampoline', 'carousel', 'carnival', 'locomotive', 'hot air balloon', 'praying mantis', 'animator', 'artisan', 'artist', 'colorist', 'inker', 'coppersmith', 'director', 'designer', 'flatter', 'stylist', 'leadman', 'limner', 'make-up artist', 'model', 'musician', 'penciller', 'producer', 'scenographer', 'set decorator', 'silversmith', 'teacher', 'auto mechanic', 'beader', 'bobbin boy', 'clerk of the chapel', 'filling station attendant', 'foreman', 'maintenance engineering', 'mechanic', 'miller', 'moldmaker', 'panel beater', 'patternmaker', 'plant operator', 'plumber', 'sawfiler', 'shop foreman', 'soaper', 'stationary engineer', 'wheelwright', 'woodworkers'];
  return `${adjectives[Math.floor(Math.random() * adjectives.length)]} ${nouns[Math.floor(Math.random() * nouns.length)]}`;
}

const hashTableLength = 15;
const listLength = 200;
const list = [];
for (let userCounter = 0; userCounter < listLength; userCounter++) {
  list.push({
    name: randomName(),
    age: Math.floor(Math.random() * 200),
  });
}
const hashTable = [];

/**
 * Hashes the object
 * @param  {name:'NAME'} obj [Object containing a name property]
 * @return {String}     [Hash value based on the object]
 */
function hash(obj) {
  return obj.name.charCodeAt(0) % hashTableLength;
}

/**
 * Insert data into hash table
 * @param  {name: 'NAME'} data [Data you want to put into table.  Contains at least { name }]
 */
function insert(data) {
  const value = hash(data);
  if (!hashTable[value]) {
    hashTable[value] = (new Link({ data, next: null, previous: null }));
    return;
  } else {
    hashTable[value].insert(data);
  }
}

/**
 * Get the object that was put into table.
 * @param  {name: 'NAME'} data [Requires at least { name }]
 * @return {[type]}      [description]
 */
function getValue(data) {
  const value = hash(data);
  if (!hashTable[value]) return null;
  return hashTable[value].getKey(data);
}

list.forEach(a => insert(a));

console.log(hashTable);
console.log(getValue(list[0]));
