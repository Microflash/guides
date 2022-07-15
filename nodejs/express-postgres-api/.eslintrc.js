module.exports = {
  'parser': '@typescript-eslint/parser',
  'extends': ['plugin:@typescript-eslint/eslint-recommended'],
  'parserOptions': {
    'ecmaVersion': 2018,
    'sourceType': 'module'
  },
  'plugins': ['@typescript-eslint'],
  'rules': {
    'quotes': ['error', 'single'],
    'semi': ['error', 'always'],
    '@typescript-eslint/explicit-function-return-type': 'off',
    '@typescript-eslint/no-explicit-any': 1,
    '@typescript-eslint/no-inferrable-types': [
      'warn', { 'ignoreParameters': true }
    ],
    '@typescript-eslint/no-unused-vars': 'warn'
  }
}