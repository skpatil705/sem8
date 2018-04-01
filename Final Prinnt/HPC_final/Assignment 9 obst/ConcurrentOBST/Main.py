import itertools as it
from multiprocessing.pool import Pool
import time

# TODO:
# 1. A way to add dummy (E) nodes. [x]
# 2. A way to calculate cost of a tree. [x]
# 3. Permutation generator (itertools). [X]
# 4. Final input/output. [ ]

class BSTreeNode:
    
    def __init__(self, key, left=None, right=None):
        self.key = key
        self.left = left
        self.right = right
        self.level = 1
    
    def insert(self, key):
        if self.key is None:
            self.key = key
        elif self.key.val > key.val:
            if self.left is None:
                self.left = BSTreeNode(key)
                self.left.level = self.level + 1
            else:
                self.left.insert(key)
        elif self.key.val < key.val:
            if self.right is None:
                self.right = BSTreeNode(key)
                self.right.level = self.level + 1
            else:
                self.right.insert(key)
    
    def complete_tree(self, q):
        if self.left is None:
            self.left = BSTreeNode(DummyKey(q.pop(0)))
            self.left.level = self.level + 1
        else:
            self.left.complete_tree(q)

        if self.right is None:
            self.right = BSTreeNode(DummyKey(q.pop(0)))
            self.right.level = self.level + 1
        else:
            self.right.complete_tree(q)

    def inorder(self):
        #print((str(self.key), self.level))
        self.pretty_print()
        if self.left is not None:
            self.left.inorder()
        if self.right is not None:
            self.right.inorder()

    def pretty_print(self):
       if isinstance(self.key, Key):
            print("Key::Val: {}, p: {}, level: {}".format(self.key.val, self.key.p, self.level))
       else:
            print("DummyKey::p: {}, level: {}".format(self.key.q, self.level)) 

    def cost(self):
        return self._cost(0)

    def _cost(self, cost):
        total_cost = cost
        if isinstance(self.key, Key):
            total_cost += self.level * self.key.p
        if isinstance(self.key, DummyKey):
            total_cost += (self.level - 1) * self.key.q

        if self.left is not None:
            total_cost = self.left._cost(total_cost)
        if self.right is not None:
            total_cost = self.right._cost(total_cost)

        return total_cost 
    
    def __repr__(self):
        return 'BSTreeNode(key: {})'.format(self.key)

class Key:
    def __init__(self, value, p):
        self.val = value
        self.p = p

    def __str__(self):
        return 'Key(val: {}, p: {})'.format(self.val, self.p)
    
    def __repr__(self):
        return self.__str__()

class DummyKey:
    def __init__(self, q):
        self.q = q

    def __str__(self):
        return 'DummyKey({})'.format(self.q)
    
    def __repr__(self):
        return self.__str__()

class OBSTSolver:
    
    def __init__(self, keys=None, q=None):
        super().__init__()
        assert keys is not None, 'keys cannot be None'
        self.keys = keys
        self.q = q
        self.root = None
    
    def _build_tree(self):
        root = BSTreeNode(None)
        for key in self.keys:
            root.insert(key)
        root.complete_tree(self.q)

        return root

    def get_cost(self):
        self.root = self._build_tree()
        return self.root.cost()

def solve(solver: OBSTSolver) -> (BSTreeNode, int):
    cost = solver.get_cost()
    return (solver.root, cost)

def run():
    nkeys = int(input('Enter number of keys: '))
    keys = input('Enter the keys: ').strip().split()
    assert len(keys) == nkeys, 'len(keys) != nkeys'
    p = list(map(float, input('Enter success probabilities (p): ').split()))
    assert len(p) == nkeys, 'len(p) != nkeys'
    q = list(map(float, input('Enter failure probabilities (q): ').split()))
    assert len(q) == (nkeys + 1), 'len(q) != nkeys + 1'

    key_array = [Key(val, p1) for val, p1 in zip(keys, p)]
    
    all_permutations = it.permutations(key_array)

    pool = Pool(processes=8)
    
    start_time = time.time()
    result = pool.map_async(solve, [OBSTSolver(keys=perm, q=q[:]) for perm in all_permutations])
    pool.close()
    pool.join()
    end_time = time.time()


    results = result.get(1)

    print("\n*** OBST Calculated ***")
    min_root, min_cost = min(results, key=lambda x: x[1])
    print("Minimum Cost: {}\n".format(min_cost))
    min_root.inorder()
    print("")
    print("Time taken: {} ms.".format((end_time - start_time)*1000))
    
if __name__ == '__main__':
    # root = BSTreeNode(Key('do', 1./7))
    # root.insert(Key('while', 1./7))
    # root.insert(Key('if', 1./7))
    # #root.insert(2)
    # q = [1./7, 1./7, 1./7, 1./7]
    # root.complete_tree(q)
    # root.inorder()
    # print(root.cost())
    
    # t = OBSTSolver(keys=['do', 'while', 'if'], p=[1./7, 1./7, 1./7], q=[1./7, 1./7, 1./7, 1./7], callback=lambda x: print(x))
    # t.start()
    # t.join()


    run()
