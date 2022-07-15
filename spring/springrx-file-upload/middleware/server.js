const express = require('express')
const multer = require('multer')
const cors = require('cors')
const bodyParser = require('body-parser')
const morgan = require('morgan')
const axios = require('axios')
const FormData = require('form-data')
const fs = require('fs')
const path = require('path')

const app = express()
app.use(cors())
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({ extended: true }))
app.use(morgan('combined'))

const port = process.env.PORT || 8080
const tempDir = 'tmp'
const context = '/file'
const backend = `http://localhost:8079${context}`
const storage = multer.diskStorage({
  destination: (req, file, cb) => cb(null, `${tempDir}/`),
  filename: (req, file, cb) => cb(null, file.originalname)
})

const outPath = path.resolve(process.cwd(), tempDir)
if (fs.existsSync(outPath)) {
  fs.rmdirSync(outPath, { recursive: true })
}
fs.mkdirSync(outPath)

const upload = multer({ storage: storage })

app.get(context, async (req, res) => {
  const response = await axios.get(backend)
  res.json(response.data)
})

app.get(`${context}/download`, async (req, res) => {
  if (req.query && req.query.fileName) {
    const response = await axios.get(`${backend}/download?fileName=${req.query.fileName}`)
    res.set({ ...response.headers }).send(response.data)
  }
})

app.post(context, upload.array('data'), async (req, res) => {
  try {
    const data = req.files

    if (data) {
      const form = new FormData()
      data.forEach(file => form.append('data', fs.createReadStream(__dirname + '/' + file.destination + file.filename)))

      const response = await axios.post(backend, form, { headers: form.getHeaders() })
      res.json(response.data)
    }
  } catch (err) {
    res.status(500).send(err);
  }
})

app.listen(port, () => console.log(`App started on port ${port}`))
